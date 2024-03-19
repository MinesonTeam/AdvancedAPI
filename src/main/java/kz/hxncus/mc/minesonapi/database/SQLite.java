package kz.hxncus.mc.minesonapi.database;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Map;

public class SQLite extends AbstractDatabase {
    public SQLite(@NonNull String directory, @NonNull String database, @NonNull String username, @NonNull String password, @Nullable Map<String, String> properties, @Nullable String tableSQL) {
        super("jdbc:sqlite:plugins/" + directory + "/" + database, username, password, properties, tableSQL);
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.SQLITE;
    }
}
