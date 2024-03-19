package kz.hxncus.mc.minesonapi.database;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Map;

public class MySQL extends AbstractDatabase {
    public MySQL(@NonNull String host, @NonNull String port, @NonNull String database, @NonNull String username, @NonNull String password, @Nullable Map<String, String> properties, @Nullable String tableSQL) {
        super("jdbc:mysql://" + host + ":" + port + "/" + database, username, password, properties, tableSQL);
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MYSQL;
    }
}
