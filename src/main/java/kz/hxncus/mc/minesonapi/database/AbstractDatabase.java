package kz.hxncus.mc.minesonapi.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.rules.RunRules;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractDatabase implements Database {
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

    protected AbstractDatabase(@NonNull String url, @NonNull String username, @NonNull String password, @Nullable Map<String, String> properties, @Nullable String tableSQL) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.properties = properties;
        createConnection();
        if (tableSQL != null) {
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
        if (this.dataSource != null && !this.dataSource.isClosed()) {
            this.dataSource.close();
        }
    }

    @SneakyThrows
    @Override
    public int execute(String sql) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).execute(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    @SneakyThrows
    @Override
    public Result<Record> fetch(String sql) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetch(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public Record fetchOne(String sql) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).fetchOne(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public int execute(String sql, Object... bindings) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).execute(sql, bindings);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @SneakyThrows
    @Override
    public int execute(String sql, QueryPart... parts) {
        try (Connection connection = this.dataSource.getConnection()) {
            return getDSLContext(connection).execute(sql, parts);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public DSLContext getDSLContext(Connection connection) {
        return DSL.using(connection, SQLDialect.valueOf(getType().name()));
    }

    @Override
    public void reload() {
        closeConnection();
        createConnection();
    }
}
