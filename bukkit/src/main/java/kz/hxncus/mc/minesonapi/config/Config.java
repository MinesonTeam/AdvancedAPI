package kz.hxncus.mc.minesonapi.config;

import kz.hxncus.mc.minesonapi.util.UUIDUtil;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class Config extends YamlConfiguration {
    private final File file;

    public Config(File parent, String child) {
        this.file = new File(parent, child);
        reloadConfig();
    }

    public Config(String path) {
        this.file = new File(path);
        reloadConfig();
    }

    public Config(URI uri) {
        this.file = new File(uri);
        reloadConfig();
    }

    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFile() {
        try {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void reloadConfig() {
        createFile();
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAndSave(@NonNull String path, @NonNull Object value) {
        set(path, value);
        save();
    }

    public UUID getUuid(@NonNull String path) {
        try {
            return UUID.fromString(get(path, "").toString());
        } catch (IllegalArgumentException e) {
            return UUIDUtil.EMPTY_UUID;
        }
    }

    public boolean isUuid(@NonNull String path) {
        return getUuid(path) != UUIDUtil.EMPTY_UUID;
    }

    @NonNull
    public Material getMaterial(@NonNull String path, @NonNull Material def) {
        Material material = getMaterial(path);
        return material == null ? def : material;
    }

    public Material getMaterial(@NonNull String path) {
        return Material.getMaterial(getString(path, ""));
    }

    public boolean isMaterial(@NonNull String path) {
        return getMaterial(path) != null;
    }

    @NonNull
    public Set<String> getKeys(String path, boolean deep) {
        ConfigurationSection section = getConfigurationSection(path);
        if (section == null) {
            return Collections.emptySet();
        }
        return section.getKeys(deep);
    }

    public List<Material> getMaterialList(@NonNull String path) {
        List<Material> list = new LinkedList<>();
        for (String key : getKeys(path, false)) {
            list.add(getMaterial(path + "." + key));
        }
        return list;
    }

    @NonNull
    public Entity getEntity(@NonNull String path, @NonNull Entity def) {
        Entity entity = Bukkit.getEntity(getUuid(path));
        return entity == null ? def : entity;
    }

    public Entity getEntity(@NonNull String path) {
        return Bukkit.getEntity(getUuid(path));
    }

    public boolean isEntity(@NonNull String path) {
        return getEntity(path) != null;
    }

    public List<Entity> getEntityList(@NonNull String path) {
        List<Entity> list = new LinkedList<>();
        for (String key : getKeys(path, false)) {
            list.add(getEntity(path + "." + key));
        }
        return list;
    }

    @Override
    @NonNull
    public OfflinePlayer getOfflinePlayer(@NonNull String path) {
        UUID uuid = getUuid(path);
        if (uuid == UUIDUtil.EMPTY_UUID) {
            return Bukkit.getOfflinePlayer(getString(path, ""));
        } else {
            return Bukkit.getOfflinePlayer(uuid);
        }
    }

    public List<OfflinePlayer> getOfflinePlayerList(@NonNull String path) {
        List<OfflinePlayer> list = new LinkedList<>();
        for (String key : getKeys(path, false)) {
            list.add(getOfflinePlayer(path + "." + key));
        }
        return list;
    }

    public Player getOnlinePlayer(@NonNull String path) {
        UUID uuid = getUuid(path);
        if (uuid == UUIDUtil.EMPTY_UUID) {
            return Bukkit.getPlayer(getString(path, ""));
        } else {
            return Bukkit.getPlayer(uuid);
        }
    }

    @NonNull
    public Player getOnlinePlayer(@NonNull String path, Player def) {
        Player player = getOnlinePlayer(path);
        return player == null ? def : player;
    }

    public boolean isOnlinePlayer(@NonNull String path) {
        return getOnlinePlayer(path) != null;
    }

    public List<Player> getOnlinePlayerList(@NonNull String path) {
        List<Player> list = new LinkedList<>();
        for (String key : getKeys(path, false)) {
            list.add(getOnlinePlayer(path + "." + key));
        }
        return list;
    }

    public List<ItemStack> getItemStackList(@NonNull String path) {
        List<ItemStack> list = new LinkedList<>();
        for (String key : getKeys(path, false)) {
            list.add(getItemStack(path + "." + key));
        }
        return list;
    }
}
