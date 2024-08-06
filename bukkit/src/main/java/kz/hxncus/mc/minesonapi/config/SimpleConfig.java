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

public class SimpleConfig extends YamlConfiguration {
	private final File file;
	
	public SimpleConfig(final File parent, final String child) {
		this.file = new File(parent, child);
		this.reloadConfig();
	}
	
	public void reloadConfig() {
		this.createFile();
		try {
			this.load(this.file);
		} catch (final IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void createFile() {
		try {
			this.file.getParentFile()
			         .mkdirs();
			this.file.createNewFile();
		} catch (final IOException e) {
			throw new RuntimeException();
		}
	}
	
	public SimpleConfig(final String parent, final String child) {
		this.file = new File(parent, child);
		this.reloadConfig();
	}
	
	public SimpleConfig(final String path) {
		this.file = new File(path);
		this.reloadConfig();
	}
	
	public SimpleConfig(final URI uri) {
		this.file = new File(uri);
		this.reloadConfig();
	}
	
	public void setAndSave(@NonNull final String path, @NonNull final Object value) {
		this.set(path, value);
		this.save();
	}
	
	public void save() {
		try {
			this.save(this.file);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isUuid(@NonNull final String path) {
		return this.getUuid(path) != UUIDUtil.EMPTY_UUID;
	}
	
	public UUID getUuid(@NonNull final String path) {
		try {
			return UUID.fromString(this.get(path, "")
			                           .toString());
		} catch (final IllegalArgumentException e) {
			return UUIDUtil.EMPTY_UUID;
		}
	}
	
	@NonNull
	public Material getMaterial(@NonNull final String path, @NonNull final Material def) {
		final Material material = this.getMaterial(path);
		return material == null ? def : material;
	}
	
	public Material getMaterial(@NonNull final String path) {
		return Material.getMaterial(this.getString(path, ""));
	}
	
	public boolean isMaterial(@NonNull final String path) {
		return this.getMaterial(path) != null;
	}
	
	public List<Material> getMaterialList(@NonNull final String path) {
		final List<Material> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getMaterial(path + "." + key));
		}
		return list;
	}
	
	@NonNull
	public Set<String> getKeys(final String path, final boolean deep) {
		final ConfigurationSection section = this.getConfigurationSection(path);
		if (section == null) {
			return Collections.emptySet();
		}
		return section.getKeys(deep);
	}
	
	@NonNull
	public Entity getEntity(@NonNull final String path, @NonNull final Entity def) {
		final Entity entity = Bukkit.getEntity(this.getUuid(path));
		return entity == null ? def : entity;
	}
	
	public boolean isEntity(@NonNull final String path) {
		return this.getEntity(path) != null;
	}
	
	public Entity getEntity(@NonNull final String path) {
		return Bukkit.getEntity(this.getUuid(path));
	}
	
	public List<Entity> getEntityList(@NonNull final String path) {
		final List<Entity> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getEntity(path + "." + key));
		}
		return list;
	}
	
	public List<OfflinePlayer> getOfflinePlayerList(@NonNull final String path) {
		final List<OfflinePlayer> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getOfflinePlayer(path + "." + key));
		}
		return list;
	}
	
	@Override
	@NonNull
	public OfflinePlayer getOfflinePlayer(@NonNull final String path) {
		final UUID uuid = this.getUuid(path);
		if (uuid == UUIDUtil.EMPTY_UUID) {
			return Bukkit.getOfflinePlayer(this.getString(path, ""));
		} else {
			return Bukkit.getOfflinePlayer(uuid);
		}
	}
	
	@NonNull
	public Player getOnlinePlayer(@NonNull final String path, final Player def) {
		final Player player = this.getOnlinePlayer(path);
		return player == null ? def : player;
	}
	
	public Player getOnlinePlayer(@NonNull final String path) {
		final UUID uuid = this.getUuid(path);
		if (uuid == UUIDUtil.EMPTY_UUID) {
			return Bukkit.getPlayer(this.getString(path, ""));
		} else {
			return Bukkit.getPlayer(uuid);
		}
	}
	
	public boolean isOnlinePlayer(@NonNull final String path) {
		return this.getOnlinePlayer(path) != null;
	}
	
	public List<Player> getOnlinePlayerList(@NonNull final String path) {
		final List<Player> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getOnlinePlayer(path + "." + key));
		}
		return list;
	}
	
	public List<ItemStack> getItemStackList(@NonNull final String path) {
		final List<ItemStack> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getItemStack(path + "." + key));
		}
		return list;
	}
}
