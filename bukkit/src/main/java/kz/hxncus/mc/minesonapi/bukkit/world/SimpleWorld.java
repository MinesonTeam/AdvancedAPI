package kz.hxncus.mc.minesonapi.bukkit.world;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Getter
public class SimpleWorld {
	private final World world;
	private final WorldBorder worldBorder;
	
	public SimpleWorld(final WorldCreator worldCreator) {
		final String name = worldCreator.name();
		if (Bukkit.getWorlds()
		          .stream()
		          .map(World::getName)
		          .anyMatch(worldName -> worldName.equals(name))) {
			throw new RuntimeException("World " + name + " already exists");
		}
		this.world = worldCreator.createWorld();
		if (this.world == null) {
			throw new RuntimeException("World creation failed");
		}
		this.worldBorder = this.world.getWorldBorder();
	}
	
	public SimpleWorld(final World world) {
		this.world = world;
		this.worldBorder = world.getWorldBorder();
	}
	
	public String getName() {
		return this.world.getName();
	}
	
	public SimpleWorld gameMode(final GameMode gameMode) {
		for (final Player player : this.getPlayers()) {
			player.setGameMode(gameMode);
		}
		return this;
	}
	
	public List<Player> getPlayers() {
		return this.world.getPlayers();
	}
	
	public SimpleWorld setStorm(final boolean storm) {
		this.world.setStorm(storm);
		return this;
	}
	
	public SimpleWorld worldBorderCenter(final double x, final double z) {
		this.worldBorder.setCenter(x, z);
		return this;
	}
	
	public SimpleWorld worldBorderSize(final double size) {
		this.worldBorder.setSize(size);
		return this;
	}
	
	public SimpleWorld deathMessageVisibility(final boolean visible) {
		for (final Player player : this.getPlayers()) {
			player.setPlayerListName(visible ? player.getName() : "");
		}
		return this;
	}
	
	public SimpleWorld monsterSpawn(final boolean allow) {
		this.world.setGameRule(GameRule.DO_MOB_SPAWNING, allow);
		return this;
	}
	
	@Nullable
	public GameMode getGameMode() {
		return this.world.getPlayers()
		                 .isEmpty() ? null : this.getPlayers()
		                                         .getFirst()
		                                         .getGameMode();
	}
	
	public Difficulty getDifficulty() {
		return this.world.getDifficulty();
	}
	
	public SimpleWorld setDifficulty(final Difficulty difficulty) {
		this.world.setDifficulty(difficulty);
		return this;
	}
	
	public boolean isStormy() {
		return this.world.hasStorm();
	}
	
	public long getTime() {
		return this.world.getTime();
	}
	
	public SimpleWorld setTime(final long time) {
		this.world.setTime(time);
		return this;
	}
	
	public SimpleWorld setClearWeatherDuration(final int duration) {
		this.world.setClearWeatherDuration(duration);
		return this;
	}
	
	public SimpleWorld setThunderDuration(final int duration) {
		this.world.setThunderDuration(duration);
		return this;
	}
	
	public SimpleWorld setWeatherDuration(final int duration) {
		this.world.setWeatherDuration(duration);
		return this;
	}
	
	public SimpleWorld setThundering(final boolean thundering) {
		this.world.setThundering(thundering);
		return this;
	}
	
	public SimpleWorld setWeatherClear() {
		this.world.setStorm(false);
		this.world.setThundering(false);
		return this;
	}
	
	public SimpleWorld save() {
		this.world.save();
		return this;
	}
	
	public SimpleWorld resetWorldBorder() {
		this.worldBorder.reset();
		return this;
	}
	
	public SimpleWorld setWorldBorderDamageAmount(final double damageAmount) {
		this.worldBorder.setDamageAmount(damageAmount);
		return this;
	}
	
	public SimpleWorld setWorldBorderDamageBuffer(final double damageBuffer) {
		this.worldBorder.setDamageBuffer(damageBuffer);
		return this;
	}
	
	public SimpleWorld setWorldBorderWarningDistance(final int warningDistance) {
		this.worldBorder.setWarningDistance(warningDistance);
		return this;
	}
	
	public SimpleWorld setWorldBorderWarningTime(final int warningTime) {
		this.worldBorder.setWarningTime(warningTime);
		return this;
	}
	
	public String getGameRuleValue(final GameRule<?> rule) {
		return (String) this.world.getGameRuleValue(rule);
	}
	
	public <T> SimpleWorld setGameRuleValue(final GameRule<T> rule, final T value) {
		this.world.setGameRule(rule, value);
		return this;
	}
	
	public int getSeaLevel() {
		return this.world.getSeaLevel();
	}
	
	public int getMaxHeight() {
		return this.world.getMaxHeight();
	}
	
	public boolean getAllowAnimals() {
		return this.world.getAllowAnimals();
	}
	
	public SimpleWorld setAllowAnimals(final boolean allow) {
		this.setGameRule(GameRule.DO_MOB_SPAWNING, allow);
		return this;
	}
	
	public <T> SimpleWorld setGameRule(final GameRule<T> rule, final T value) {
		this.world.setGameRule(rule, value);
		return this;
	}
	
	public boolean getAllowMonsters() {
		return this.world.getAllowMonsters();
	}
	
	public SimpleWorld setAllowMonsters(final boolean allow) {
		this.setGameRule(GameRule.DO_MOB_SPAWNING, allow);
		return this;
	}
	
	public SimpleWorld playEffect(final Location location, final Effect effect, final int data) {
		this.world.playEffect(location, effect, data);
		return this;
	}
	
	public SimpleWorld playSound(final Location location, final Sound sound, final float volume, final float pitch) {
		this.world.playSound(location, sound, volume, pitch);
		return this;
	}
	
	public Entity spawnEntity(final Location location, final EntityType type) {
		return this.world.spawnEntity(location, type);
	}
	
	public List<Entity> getEntities() {
		return this.world.getEntities();
	}
	
	public List<LivingEntity> getLivingEntities() {
		return this.world.getLivingEntities();
	}
	
	public Collection<Entity> getNearbyEntities(final Location location, final double x, final double y, final double z) {
		return this.world.getNearbyEntities(location, x, y, z);
	}
	
	public SimpleWorld strikeLightning(final Location location) {
		this.world.strikeLightning(location);
		return this;
	}
	
	public SimpleWorld strikeLightningEffect(final Location location) {
		this.world.strikeLightningEffect(location);
		return this;
	}
	
	public Block getHighestBlockAt(final int x, final int z) {
		return this.world.getHighestBlockAt(x, z);
	}
	
	public int getHighestBlockYAt(final int x, final int z) {
		return this.world.getHighestBlockYAt(x, z);
	}
	
	public SimpleWorld setSpawnLocation(final int x, final int y, final int z) {
		this.world.setSpawnLocation(x, y, z);
		return this;
	}
	
	public Location getSpawnLocation() {
		return this.world.getSpawnLocation();
	}
	
	public SimpleWorld setSpawnLocation(final Location location) {
		this.world.setSpawnLocation(location);
		return this;
	}
	
	public boolean isAutoSave() {
		return this.world.isAutoSave();
	}
	
	public SimpleWorld setAutoSave(final boolean autoSave) {
		this.world.setAutoSave(autoSave);
		return this;
	}
	
	@NonNull
	public SimpleWorld setBiome(final int x, final int y, final int z, final Biome biome) {
		this.world.setBiome(x, y, z, biome);
		return this;
	}
	
	@NonNull
	public Biome getBiome(final int x, final int y, final int z) {
		return this.world.getBiome(x, y, z);
	}
	
	@NonNull
	public SimpleWorld setPVP(final boolean pvp) {
		this.world.setPVP(pvp);
		return this;
	}
	
	public boolean isPVPEnabled() {
		return this.world.getPVP();
	}
	
	public boolean isKeepSpawnInMemory() {
		return this.world.getKeepSpawnInMemory();
	}
	
	public SimpleWorld setKeepSpawnInMemory(final boolean keepSpawn) {
		this.world.setKeepSpawnInMemory(keepSpawn);
		return this;
	}
}