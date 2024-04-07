package kz.hxncus.mc.minesonapi.database;

import kz.hxncus.mc.minesonapi.database.type.DatabaseType;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;

import javax.annotation.Nullable;
import java.sql.Connection;

interface Database {
    void createConnection();
    DatabaseType getType();
    Connection getConnection();
    void closeConnection();
    DSLContext getDSLContext(@NonNull Connection connection);
    @NonNull Result<Record> fetch(@NonNull String sql);
    @NonNull Result<Record> fetch(@NonNull String sql, Object @NonNull ... bindings);
    @NonNull Result<Record> fetch(@NonNull String sql, QueryPart @NonNull ... parts);
    @Nullable Record fetchOne(@NonNull String sql);
    @Nullable Record fetchOne(@NonNull String sql, Object @NonNull ... bindings);
    @Nullable Record fetchOne(@NonNull String sql, QueryPart @NonNull ... parts);
    int execute(@NonNull String sql);
    int execute(@NonNull String sql, Object @NonNull ... bindings);
    int execute(@NonNull String sql, QueryPart @NonNull ... parts);
    int getNewId(@NonNull String table);
    void reload();
}
