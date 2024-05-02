package kz.hxncus.mc.minesonapi.database;

import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MariaDB extends DefaultDatabase {
    public MariaDB(@NonNull Plugin plugin, @NonNull String host, @NonNull String port, @NonNull String database, @NonNull String username, @NonNull String password, @Nullable Map<String, String> properties, @Nullable String tableSQL) {
        super(plugin, "jdbc:mariadb://" + host + ":" + port + "/" + database, username, password, properties, tableSQL);
    }

    @Override
    public Database.Type getType() {
        return Database.Type.MARIADB;
    }
}
