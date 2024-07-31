package kz.hxncus.mc.minesonapi.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.File;
import java.util.Objects;

public class Database {
    protected String url;
    protected String tableSQL;
    @Getter
    protected DatabaseSettings settings;
    protected HikariDataSource dataSource;
    protected DSLContext dslContext;

    public Database(@NonNull Plugin plugin, String tableSQL, @NonNull DatabaseSettings settings) {
        this.tableSQL = tableSQL;
        this.settings = settings;
        if (Objects.requireNonNull(settings.sqlDialect) == SQLDialect.SQLITE) {
            this.url = "jdbc:sqlite:plugins/" + plugin.getDataFolder().getName() + File.separator + settings.database + ".db";
        } else {
            this.url = "jdbc:" + settings.sqlDialect.getNameLC() + "://" + settings.host + ":" + settings.port + "/" + settings.database;
        }
        createConnection();
    }

    public void createConnection() {
        this.dataSource = new HikariDataSource();
        this.dataSource.setJdbcUrl(url);
        this.dataSource.setUsername(settings.username);
        this.dataSource.setPassword(settings.password);
        if (settings.properties != null) {
            settings.properties.forEach(this.dataSource::addDataSourceProperty);
        }
        this.dslContext = DSL.using(this.dataSource, settings.getSqlDialect());
        if (tableSQL != null && !tableSQL.isEmpty()) {
            execute(tableSQL);
        }
    }

    public int getNewId(@NonNull String table) {
        Record entry = fetchOne("SELECT MAX(id) as maxId FROM " + table);
        if (entry == null) {
            return 1;
        }
        Object obj = entry.get("maxId");
        return obj == null ? 1 : (int) obj + 1;
    }

    public int execute(@NonNull String sql) {
        return dslContext.execute(sql);
    }

    public @NonNull Result<Record> fetch(@NonNull String sql) {
        return dslContext.fetch(sql);
    }

    public @NonNull Result<Record> fetch(@NonNull String sql, Object @NonNull ... bindings) {
        return dslContext.fetch(sql, bindings);
    }

    public @NonNull Result<Record> fetch(@NonNull String sql, QueryPart @NonNull ... parts) {
        return dslContext.fetch(sql, parts);
    }

    public Record fetchOne(@NonNull String sql) {
        return dslContext.fetchOne(sql);
    }

    public Record fetchOne(@NonNull String sql, Object @NonNull ... bindings) {
        return dslContext.fetchOne(sql, bindings);
    }

    public Record fetchOne(@NonNull String sql, QueryPart @NonNull ... parts) {
        return dslContext.fetchOne(sql, parts);
    }

    public int execute(@NonNull String sql, Object @NonNull ... bindings) {
        return dslContext.execute(sql, bindings);
    }

    public int execute(@NonNull String sql, QueryPart @NonNull ... parts) {
        return dslContext.execute(sql, parts);
    }

    public void closeConnection() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    public void reload() {
        closeConnection();
        createConnection();
    }
}

