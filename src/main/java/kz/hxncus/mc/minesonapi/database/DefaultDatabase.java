package kz.hxncus.mc.minesonapi.database;

import com.zaxxer.hikari.HikariDataSource;
import kz.hxncus.mc.minesonapi.util.ReflectionUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public abstract class DefaultDatabase implements Database {
    @Getter
    protected Plugin plugin;
    @Getter
    @Setter
    protected String url;
    @Getter
    @Setter
    protected String username;
    @Setter
    protected String password;
    @Getter
    @Setter
    protected Map<String, String> properties;
    protected HikariDataSource dataSource;
    @Nullable
    public static Database getDatabaseByType(@NonNull Database.Type dataBaseType, @NonNull Plugin plugin, @NonNull String url, @NonNull String username, @NonNull String password, @NonNull Map<String, String> properties) {
        Constructor<?> constructor = ReflectionUtil.getConstructor(dataBaseType.clazz, plugin.getClass(), url.getClass(), username.getClass(), password.getClass(), properties.getClass());
        if (constructor == null) {
            return null;
        }
        Object obj = ReflectionUtil.newInstance(constructor, plugin, url, username, password, properties);
        if (obj instanceof Database database) {
            return database;
        }
        return null;
    }

    protected DefaultDatabase(@NonNull Plugin plugin, @NonNull String url, @NonNull String username, @NonNull String password, @NonNull Map<String, String> properties) {
        this(plugin, url, username, password, properties, null);
    }

    protected DefaultDatabase(@NonNull Plugin plugin, @NonNull String url, @NonNull String username, @NonNull String password, @NonNull Map<String, String> properties, @Nullable String tableSQL) {
        this.plugin = plugin;
        this.url = url;
        this.username = username;
        this.password = password;
        this.properties = properties;
        createConnection();
        if (tableSQL != null && !tableSQL.isEmpty()) {
            execute(tableSQL);
        }
    }

    @Override
    public void createConnection() {
        this.dataSource = new HikariDataSource();
        this.dataSource.setJdbcUrl(this.url);
        this.dataSource.setUsername(this.username);
        this.dataSource.setPassword(this.password);
        if (this.properties != null) {
            this.properties.forEach(this.dataSource::addDataSourceProperty);
        }
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void closeConnection() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    @Override
    public int getNewId(@NonNull String table) {
        Record entry = fetchOne("SELECT MAX(id) as maxId FROM " + table);
        if (entry == null) {
            return 1;
        }
        Object obj = entry.get("maxId");
        return obj == null ? 1 : (int) obj + 1;
    }

    @SneakyThrows
    @Override
    public int execute(@NonNull String sql) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).execute(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public @NonNull Result<Record> fetch(@NonNull String sql) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetch(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public @NonNull Result<Record> fetch(@NonNull String sql, Object @NonNull ... bindings) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetch(sql, bindings);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public @NonNull Result<Record> fetch(@NonNull String sql, QueryPart @NonNull ... parts) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetch(sql, parts);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public @Nullable Record fetchOne(@NonNull String sql) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetchOne(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public @Nullable Record fetchOne(@NonNull String sql, Object @NonNull ... bindings) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetchOne(sql, bindings);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public @Nullable Record fetchOne(@NonNull String sql, QueryPart @NonNull ... parts) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetchOne(sql, parts);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public int execute(@NonNull String sql, Object @NonNull ... bindings) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).execute(sql, bindings);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public int execute(@NonNull String sql, QueryPart @NonNull ... parts) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).execute(sql, parts);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public @NonNull DSLContext getDSLContext(@NonNull Connection connection) {
        return DSL.using(connection, SQLDialect.valueOf(getType().name()));
    }

    @Override
    public void reload() {
        closeConnection();
        createConnection();
    }
}
