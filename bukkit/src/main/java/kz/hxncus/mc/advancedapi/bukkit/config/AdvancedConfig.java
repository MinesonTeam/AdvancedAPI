package kz.hxncus.mc.advancedapi.bukkit.config;

import kz.hxncus.mc.advancedapi.api.bukkit.config.Config;
import kz.hxncus.mc.advancedapi.utility.UUIDUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

/**
 * The type Simple config.
 * @author Hxncus
 * @since  1.0.1
 */
@Getter
@ToString
public class AdvancedConfig extends YamlConfiguration implements Config {
	private final File file;
	
	/**
	 * Instantiates a new Simple config.
	 *
	 * @param parent the parent
	 * @param child  the child
	 */
	public AdvancedConfig(final File parent, final String child) {
		this.file = new File(parent, child);
	}
	
	/**
	 * Instantiates a new Simple config.
	 *
	 * @param parent the parent
	 * @param child  the child
	 */
	public AdvancedConfig(final String parent, final String child) {
		this.file = new File(parent, child);
	}
	
	/**
	 * Instantiates a new Simple config.
	 *
	 * @param path the path
	 */
	public AdvancedConfig(final String path) {
		this.file = new File(path);
	}
	
	/**
	 * Instantiates a new Simple config.
	 *
	 * @param uri the uri
	 */
	public AdvancedConfig(final URI uri){
		this.file = new File(uri);
	}

	@Override
	public String getFileName() {
		return this.file.getName();
	}

	@Override
	public boolean isFileExists() {
		return this.file.exists();
	}

	@Override
	public boolean createFile() throws IOException {
		return this.file.getParentFile().mkdirs() && this.file.createNewFile();
	}
	
	/**
	 * Load config.
	 *
	 * @throws IOException      the io exception
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	@Override
	public boolean load() throws IOException, InvalidConfigurationException {
		this.createFile();
		if (this.isFileExists()) {
			this.load(this.file);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean loadDefaults(Plugin plugin) throws IOException {
		if (!this.isFileExists()) {
			String fileName = this.file.getName();
			InputStream resource = plugin.getResource(fileName);
			if (resource != null) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(resource)) {
					this.load(inputStreamReader);
					return true;
				} catch (InvalidConfigurationException ignored) {
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean load(Plugin plugin) throws  IOException, InvalidConfigurationException {
		return !this.loadDefaults(plugin) && this.load();
	}
	
	/**
	 * Save.
	 */
	@Override
	public void save() throws IOException {
		this.createFile();
		if (this.isFileExists()) {
			this.save(this.file);
		}
	}
	
	@Override
	public void setAndSave(@NonNull final String path, @NonNull final Object value) {
		this.set(path, value);
		try {
			this.save();
		} catch (final IOException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Is unique id boolean.
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean isUniqueId(@NonNull final String path) {
		return this.getUniqueId(path) != UUIDUtil.EMPTY_UNIQUE_ID;
	}
	
	/**
	 * Gets unique id.
	 *
	 * @param path the path
	 * @return the unique id
	 */
	public UUID getUniqueId(@NonNull final String path) {
		try {
			return UUID.fromString(this.getString(path, ""));
		} catch (final IllegalArgumentException e) {
			return UUIDUtil.EMPTY_UNIQUE_ID;
		}
	}
	
	/**
	 * Gets material.
	 *
	 * @param path the path
	 * @param def  the def
	 * @return the material
	 */
	@NonNull
	public Material getMaterial(@NonNull final String path, @NonNull final Material def) {
		final Material material = this.getMaterial(path);
		return material == null ? def : material;
	}
	
	/**
	 * Gets material.
	 *
	 * @param path the path
	 * @return the material
	 */
	public Material getMaterial(@NonNull final String path) {
		return Material.getMaterial(this.getString(path, ""));
	}
	
	/**
	 * Is material boolean.
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean isMaterial(@NonNull final String path) {
		return this.getMaterial(path) != null;
	}
	
	/**
	 * Gets a material list.
	 *
	 * @param path the path
	 * @return the material list
	 */
	public List<Material> getMaterialList(@NonNull final String path) {
		final List<Material> list = new LinkedList<>();
		final String finalPath = path + ".";
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getMaterial(finalPath + key));
		}
		return list;
	}
	
	/**
	 * Gets keys.
	 *
	 * @param path the path
	 * @param deep the deep
	 * @return the keys
	 */
	@NonNull
	public Set<String> getKeys(final String path, final boolean deep) {
		final ConfigurationSection section = this.getConfigurationSection(path);
		if (section == null) {
			return Collections.emptySet();
		}
		return section.getKeys(deep);
	}
	
	/**
	 * Gets entity.
	 *
	 * @param path the path
	 * @param def  the def
	 * @return the entity
	 */
	@NonNull
	public Entity getEntity(@NonNull final String path, @NonNull final Entity def) {
		final Entity entity = Bukkit.getEntity(this.getUniqueId(path));
		return entity == null ? def : entity;
	}
	
	/**
	 * Is entity boolean?
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean isEntity(@NonNull final String path) {
		return this.getEntity(path) != null;
	}
	
	/**
	 * Gets entity.
	 *
	 * @param path the path
	 * @return the entity
	 */
	public Entity getEntity(@NonNull final String path) {
		return Bukkit.getEntity(this.getUniqueId(path));
	}
	
	/**
	 * Gets an entity list.
	 *
	 * @param path the path
	 * @return the entity list
	 */
	public List<Entity> getEntityList(@NonNull final String path) {
		final List<Entity> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getEntity(path + "." + key));
		}
		return list;
	}
	
	/**
	 * Gets an offline player list.
	 *
	 * @param path the path
	 * @return the offline player list
	 */
	public List<OfflinePlayer> getOfflinePlayerList(@NonNull final String path) {
		final List<OfflinePlayer> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getOfflinePlayer(path + "." + key));
		}
		return list;
	}
	
	@Override
	public OfflinePlayer getOfflinePlayer(@NonNull final String path) {
		final UUID uniqueId = this.getUniqueId(path);
		if (uniqueId != UUIDUtil.EMPTY_UNIQUE_ID) {
			return Bukkit.getOfflinePlayer(uniqueId);
		}
		return null;
	}
	
	/**
	 * Gets online player.
	 *
	 * @param path the path
	 * @param def  the def
	 * @return the online player
	 */
	@NonNull
	public Player getOnlinePlayer(@NonNull final String path, final Player def) {
		final Player player = this.getOnlinePlayer(path);
		return player == null ? def : player;
	}
	
	/**
	 * Gets online player.
	 *
	 * @param path the path
	 * @return the online player
	 */
	public Player getOnlinePlayer(@NonNull final String path) {
		final UUID uniqueId = this.getUniqueId(path);
		if (uniqueId == UUIDUtil.EMPTY_UNIQUE_ID) {
			return Bukkit.getPlayer(this.getString(path, ""));
		} else {
			return Bukkit.getPlayer(uniqueId);
		}
	}
	
	/**
	 * Is online player boolean?
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean isOnlinePlayer(@NonNull final String path) {
		return this.getOnlinePlayer(path) != null;
	}
	
	/**
	 * Gets an online player list.
	 *
	 * @param path the path
	 * @return the online player list
	 */
	public List<Player> getOnlinePlayerList(@NonNull final String path) {
		final List<Player> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getOnlinePlayer(path + "." + key));
		}
		return list;
	}
	
	/**
	 * Gets item stack list.
	 *
	 * @param path the path
	 * @return the item stack list
	 */
	public List<ItemStack> getItemStackList(@NonNull final String path) {
		final List<ItemStack> list = new LinkedList<>();
		for (final String key : this.getKeys(path, false)) {
			list.add(this.getItemStack(path + "." + key));
		}
		return list;
	}
}
