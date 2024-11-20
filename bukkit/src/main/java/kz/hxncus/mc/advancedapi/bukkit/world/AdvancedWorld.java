package kz.hxncus.mc.advancedapi.bukkit.world;

import com.google.common.base.Predicates;
import kz.hxncus.mc.advancedapi.utility.WorldUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class Advanced world.
 *
 * @author GeliusIHe
 * @since 1.0.1
 */
@Getter
@ToString
public class AdvancedWorld {
	private final World world;
	private final WorldBorder worldBorder;
	
	/**
	 * Instantiates a new Simple world.
	 *
	 * @param worldCreator the world creator
	 */
	public AdvancedWorld(final WorldCreator worldCreator) {
		this.world = WorldUtil.getOrCreateWorld(worldCreator);
		this.worldBorder = this.world.getWorldBorder();
	}
	
	/**
	 * Instantiates a new Simple world.
	 *
	 * @param world the world
	 */
	public AdvancedWorld(final World world) {
		this.world = world;
		this.worldBorder = world.getWorldBorder();
	}
	
	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getWorldName() {
		return this.world.getName();
	}
	
	/**
	 * Game mode simple world.
	 *
	 * @param gameMode the game mode
	 * @return the simple world
	 */
	public AdvancedWorld setPlayersGameMode(final GameMode gameMode) {
		for (final Player player : this.getPlayers()) {
			player.setGameMode(gameMode);
		}
		return this;
	}
	
	/**
	 * Gets players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return this.world.getPlayers();
	}
	
	/**
	 * Sets storm.
	 *
	 * @param storm the storm
	 * @return the storm
	 */
	public AdvancedWorld setStorm(final boolean storm) {
		this.world.setStorm(storm);
		return this;
	}
	
	/**
	 * World border center simple world.
	 *
	 * @param x the x
	 * @param z the z
	 * @return the simple world
	 */
	public AdvancedWorld setWorldBorderCenter(final double x, final double z) {
		this.worldBorder.setCenter(x, z);
		return this;
	}
	
	/**
	 * World border size simple world.
	 *
	 * @param size the size
	 * @return the simple world
	 */
	public AdvancedWorld setWorldBorderSize(final double size) {
		this.worldBorder.setSize(size);
		return this;
	}
	
	/**
	 * Gets difficulty.
	 *
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return this.world.getDifficulty();
	}
	
	/**
	 * Sets difficulty.
	 *
	 * @param difficulty the difficulty
	 * @return the difficulty
	 */
	public AdvancedWorld setDifficulty(final Difficulty difficulty) {
		this.world.setDifficulty(difficulty);
		return this;
	}
	
	/**
	 * Is stormy boolean?
	 *
	 * @return the boolean
	 */
	public boolean hasStorm() {
		return this.world.hasStorm();
	}
	
	/**
	 * Gets time.
	 *
	 * @return the time
	 */
	public long getTime() {
		return this.world.getTime();
	}
	
	/**
	 * Sets time.
	 *
	 * @param time the time
	 * @return the time
	 */
	public AdvancedWorld setTime(final long time) {
		this.world.setTime(time);
		return this;
	}
	
	/**
	 * Sets clear weather duration.
	 *
	 * @param duration the duration
	 * @return the clear weather duration
	 */
	public AdvancedWorld setClearWeatherDuration(final int duration) {
		this.world.setClearWeatherDuration(duration);
		return this;
	}
	
	/**
	 * Sets thunder duration.
	 *
	 * @param duration the duration
	 * @return the thunder duration
	 */
	public AdvancedWorld setThunderDuration(final int duration) {
		this.world.setThunderDuration(duration);
		return this;
	}
	
	/**
	 * Sets weather duration.
	 *
	 * @param duration the duration
	 * @return the weather duration
	 */
	public AdvancedWorld setWeatherDuration(final int duration) {
		this.world.setWeatherDuration(duration);
		return this;
	}
	
	/**
	 * Sets thundering.
	 *
	 * @param thundering the thundering
	 * @return the thundering
	 */
	public AdvancedWorld setThundering(final boolean thundering) {
		this.world.setThundering(thundering);
		return this;
	}
	
	/**
	 * Sets weather clear.
	 *
	 * @return the weather clear
	 */
	public AdvancedWorld setWeatherClear() {
		this.world.setStorm(false);
		this.world.setThundering(false);
		return this;
	}
	
	/**
	 * Save a simple world.
	 *
	 * @return the simple world
	 */
	public AdvancedWorld save() {
		this.world.save();
		return this;
	}
	
	/**
	 * Reset world border simple world.
	 *
	 * @return the simple world
	 */
	public AdvancedWorld resetWorldBorder() {
		this.worldBorder.reset();
		return this;
	}
	
	/**
	 * Sets world border damage amount.
	 *
	 * @param damageAmount the damage amount
	 * @return the world border damage amount
	 */
	public AdvancedWorld setWorldBorderDamageAmount(final double damageAmount) {
		this.worldBorder.setDamageAmount(damageAmount);
		return this;
	}
	
	/**
	 * Sets world border damage buffer.
	 *
	 * @param damageBuffer the damage buffer
	 * @return the world border damage buffer
	 */
	public AdvancedWorld setWorldBorderDamageBuffer(final double damageBuffer) {
		this.worldBorder.setDamageBuffer(damageBuffer);
		return this;
	}
	
	/**
	 * Sets world border warning distance.
	 *
	 * @param warningDistance the warning distance
	 * @return the world border warning distance
	 */
	public AdvancedWorld setWorldBorderWarningDistance(final int warningDistance) {
		this.worldBorder.setWarningDistance(warningDistance);
		return this;
	}
	
	/**
	 * Sets world border warning time.
	 *
	 * @param warningTime the warning time
	 * @return the world border warning time
	 */
	public AdvancedWorld setWorldBorderWarningTime(final int warningTime) {
		this.worldBorder.setWarningTime(warningTime);
		return this;
	}
	
	/**
	 * Gets game gameRule value.
	 *
	 * @param gameRule the gameRule
	 * @return the game gameRule value
	 */
	public <T> T getGameRule(final GameRule<T> gameRule) {
		return this.world.getGameRuleValue(gameRule);
	}
	
	/**
	 * Sets game gameRule value.
	 *
	 * @param <T>   the type parameter
	 * @param gameRule  the gameRule
	 * @param value the value
	 * @return the game gameRule value
	 */
	public <T> AdvancedWorld setGameRule(final GameRule<T> gameRule, final T value) {
		this.world.setGameRule(gameRule, value);
		return this;
	}
	
	public AdvancedWorld setGameRules(final boolean value, final GameRule<Boolean>... gameRules) {
		for (GameRule<Boolean> gameRule : gameRules) {
			this.setGameRule(gameRule, value);
		}
		return this;
	}
	
	public AdvancedWorld setGameRules(final int value, final GameRule<Integer>... gameRules) {
		for (GameRule<Integer> gameRule : gameRules) {
			this.setGameRule(gameRule, value);
		}
		return this;
	}
	
	/**
	 * Gets sea level.
	 *
	 * @return the sea level
	 */
	public int getSeaLevel() {
		return this.world.getSeaLevel();
	}
	
	/**
	 * Gets max height.
	 *
	 * @return the max height
	 */
	public int getMaxHeight() {
		return this.world.getMaxHeight();
	}
	
	/**
	 * Gets allow animals.
	 *
	 * @return the animals allowed?
	 */
	public boolean isAnimalsAllowed() {
		return this.world.getAllowAnimals();
	}
	
	/**
	 * Gets allow monsters.
	 *
	 * @return allowing monsters
	 */
	public boolean isMonstersAllowed() {
		return this.world.getAllowMonsters();
	}
	
	/**
	 * Play affects a simple world.
	 *
	 * @param location the location
	 * @param effect   the effect
	 * @param data     the data
	 * @return the simple world
	 */
	public AdvancedWorld playEffect(final Location location, final Effect effect, final int data) {
		this.world.playEffect(location, effect, data);
		return this;
	}
	
	/**
	 * Play soundly simple world.
	 *
	 * @param location the location
	 * @param sound    the sound
	 * @param volume   the volume
	 * @param pitch    the pitch
	 * @return the simple world
	 */
	public AdvancedWorld playSound(final Location location, final Sound sound, final float volume, final float pitch) {
		this.world.playSound(location, sound, volume, pitch);
		return this;
	}
	
	/**
	 * Spawn entity.
	 *
	 * @param location the location
	 * @param type     the type
	 * @return the entity
	 */
	public Entity spawnEntity(final Location location, final EntityType type) {
		return this.world.spawnEntity(location, type);
	}
	
	/**
	 * Gets entities.
	 *
	 * @return the entities
	 */
	public List<Entity> getEntities() {
		return this.world.getEntities();
	}
	
	/**
	 * Gets living entities.
	 *
	 * @return the living entities
	 */
	public List<LivingEntity> getLivingEntities() {
		return this.world.getLivingEntities();
	}
	
	public Collection<Entity> getNearbyEntities(final BoundingBox boundingBox, Predicate< ? super Entity> filter) {
		return this.world.getNearbyEntities(boundingBox, filter);
	}
	
	public Collection<Entity> getNearbyEntities(final BoundingBox boundingBox) {
		return this.world.getNearbyEntities(boundingBox, Predicates.alwaysTrue());
	}
	
	/**
	 * Gets nearby entities.
	 *
	 * @param location the location
	 * @param x        the x
	 * @param y        the y
	 * @param z        the z
	 * @return the nearby entities
	 */
	public Collection<Entity> getNearbyEntities(final Location location, final double x, final double y, final double z, Predicate< ? super Entity> filter) {
		return this.world.getNearbyEntities(location, x, y, z, filter);
	}
	
	public Collection<Entity> getNearbyEntities(final Location location, final double x, final double y, final double z) {
		return this.world.getNearbyEntities(location, x, y, z, Predicates.alwaysTrue());
	}
	
	/**
	 * Strike lightning simple world.
	 *
	 * @param location the location
	 * @return the simple world
	 */
	public AdvancedWorld strikeLightning(final Location location) {
		this.world.strikeLightning(location);
		return this;
	}
	
	/**
	 * Strike lightning effect simple world.
	 *
	 * @param location the location
	 * @return the simple world
	 */
	public AdvancedWorld strikeLightningEffect(final Location location) {
		this.world.strikeLightningEffect(location);
		return this;
	}
	
	/**
	 * Gets highest block at.
	 *
	 * @param x the x
	 * @param z the z
	 * @return the highest block at
	 */
	public Block getHighestBlockAt(final int x, final int z) {
		return this.world.getHighestBlockAt(x, z);
	}
	
	/**
	 * Gets highest block y at.
	 *
	 * @param x the x
	 * @param z the z
	 * @return the highest block y at
	 */
	public int getHighestBlockYAt(final int x, final int z) {
		return this.world.getHighestBlockYAt(x, z);
	}
	
	/**
	 * Sets spawn location.
	 *
	 * @param location the location
	 * @return the spawn location
	 */
	public AdvancedWorld setSpawnLocation(final Location location) {
		return this.setSpawnLocation(location);
	}
	
	/**
	 * Sets spawn location.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the spawn location
	 */
	public AdvancedWorld setSpawnLocation(final int x, final int y, final int z) {
		return this.setSpawnLocation(x, y, z, 0.0F);
	}
	
	/**
	 * Sets spawn location.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param angle the angle
	 * @return the spawn location
	 */
	public AdvancedWorld setSpawnLocation(final int x, final int y, final int z, final float angle) {
		this.world.setSpawnLocation(x, y, z, angle);
		return this;
	}
	
	/**
	 * Gets spawn location.
	 *
	 * @return the spawn location
	 */
	public Location getSpawnLocation() {
		return this.world.getSpawnLocation();
	}
	
	/**
	 * Is auto save boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAutoSave() {
		return this.world.isAutoSave();
	}
	
	/**
	 * Sets auto save.
	 *
	 * @param autoSave the auto save
	 * @return the auto save
	 */
	public AdvancedWorld setAutoSave(final boolean autoSave) {
		this.world.setAutoSave(autoSave);
		return this;
	}
	
	/**
	 * Sets biome.
	 *
	 * @param x     the x
	 * @param y     the y
	 * @param z     the z
	 * @param biome the biome
	 * @return the biome
	 */
	@NonNull
	public AdvancedWorld setBiome(final int x, final int y, final int z, final Biome biome) {
		this.world.setBiome(x, y, z, biome);
		return this;
	}
	
	/**
	 * Gets biome.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the biome
	 */
	@NonNull
	public Biome getBiome(final int x, final int y, final int z) {
		return this.world.getBiome(x, y, z);
	}
	
	/**
	 * Sets biome.
	 *
	 * @param location the location
	 * @param biome the biome
	 * @return the biome
	 */
	@NonNull
	public AdvancedWorld setBiome(final Location location, final Biome biome) {
		this.world.setBiome(location, biome);
		return this;
	}
	
	/**
	 * Gets biome.
	 *
	 * @param location the location
	 * @return the biome
	 */
	@NonNull
	public Biome getBiome(final Location location) {
		return this.world.getBiome(location);
	}
	
	/**
	 * Sets pvp.
	 *
	 * @param pvp the pvp
	 * @return the pvp
	 */
	@NonNull
	public AdvancedWorld setPVP(final boolean pvp) {
		this.world.setPVP(pvp);
		return this;
	}
	
	/**
	 * Is pvp enabled boolean?
	 *
	 * @return the boolean
	 */
	public boolean isPVPEnabled() {
		return this.world.getPVP();
	}
	
	/**
	 * Is keep spawn in memory boolean.
	 *
	 * @return the boolean
	 */
	public boolean isKeepSpawnInMemory() {
		return this.world.getKeepSpawnInMemory();
	}
	
	/**
	 * Sets keep spawn in memory.
	 *
	 * @param keepSpawn the keep spawn
	 * @return the keep spawn in memory
	 */
	public AdvancedWorld setKeepSpawnInMemory(final boolean keepSpawn) {
		this.world.setKeepSpawnInMemory(keepSpawn);
		return this;
	}
}