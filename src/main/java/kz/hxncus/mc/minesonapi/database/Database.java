package kz.hxncus.mc.minesonapi.database;

import org.jooq.DSLContext;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.SQLException;

interface Database {
    void createConnection();
    DatabaseType getType();
    Connection getConnection();
    void closeConnection();
    DSLContext getDSLContext(Connection connection);
    Result<Record> fetch(String sql);
    Record fetchOne(String sql);
    int execute(String sql);
    int execute(String sql, Object...bindings);
    int execute(String sql, QueryPart...parts);
    void reload();
}
