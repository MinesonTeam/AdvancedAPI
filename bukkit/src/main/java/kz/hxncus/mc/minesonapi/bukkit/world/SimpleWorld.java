package kz.hxncus.mc.minesonapi.bukkit.world;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.util.Collection;
import java.util.List;

@Getter
public class SimpleWorld {
    private final World world;

    public SimpleWorld(String name) {
        this(name, World.Environment.NORMAL, WorldType.NORMAL, null);
    }

    public SimpleWorld(String name, World.Environment environment, WorldType type, ChunkGenerator generator) {
        WorldCreator creator = new WorldCreator(name).environment(environment).type(type);

        if (generator != null) {
            creator.generator(generator);
        }
        this.world = creator.createWorld();
    }

    public SimpleWorld(World world) {
        this.world = world;
    }

    public String getName() {
        return world.getName();
    }

    public SimpleWorld gameMode(GameMode gameMode) {
        for (Player player : world.getPlayers()) {
            player.setGameMode(gameMode);
        }
        return this;
    }

    public SimpleWorld difficulty(Difficulty difficulty) {
        world.setDifficulty(difficulty);
        return this;
    }

    public SimpleWorld storm(boolean storm) {
        world.setStorm(storm);
        return this;
    }

    public SimpleWorld time(long time) {
        world.setTime(time);
        return this;
    }

    public <T> SimpleWorld setGameRule(GameRule<T> rule, T value) {
        world.setGameRule(rule, value);
        return this;
    }

    public SimpleWorld worldBorderCenter(double x, double z) {
        world.getWorldBorder().setCenter(x, z);
        return this;
    }

    public SimpleWorld worldBorderSize(double size) {
        world.getWorldBorder().setSize(size);
        return this;
    }

    public SimpleWorld deathMessageVisibility(boolean visible) {
        for (Player player : world.getPlayers()) {
            player.setPlayerListName(visible ? player.getName() : "");
        }
        return this;
    }

    public SimpleWorld monsterSpawn(boolean allow) {
        world.setGameRule(GameRule.DO_MOB_SPAWNING, allow);
        return this;
    }

    public GameMode getGameMode() {
        return world.getPlayers().isEmpty() ? null : world.getPlayers().get(0).getGameMode();
    }

    public Difficulty getDifficulty() {
        return world.getDifficulty();
    }

    public boolean isStormy() {
        return world.hasStorm();
    }

    public long getTime() {
        return world.getTime();
    }

    public SimpleWorld setClearWeatherDuration(int duration) {
        world.setClearWeatherDuration(duration);
        return this;
    }

    public SimpleWorld setThunderDuration(int duration) {
        world.setThunderDuration(duration);
        return this;
    }

    public SimpleWorld setWeatherDuration(int duration) {
        world.setWeatherDuration(duration);
        return this;
    }

    public SimpleWorld setThundering(boolean thundering) {
        world.setThundering(thundering);
        return this;
    }

    public SimpleWorld setWeatherClear() {
        world.setStorm(false);
        world.setThundering(false);
        return this;
    }

    public SimpleWorld save() {
        world.save();
        return this;
    }

    public SimpleWorld resetWorldBorder() {
        world.getWorldBorder().reset();
        return this;
    }

    public SimpleWorld setWorldBorderDamageAmount(double damageAmount) {
        world.getWorldBorder().setDamageAmount(damageAmount);
        return this;
    }
    public SimpleWorld setWorldBorderDamageBuffer(double damageBuffer) {
        world.getWorldBorder().setDamageBuffer(damageBuffer);
        return this;
    }

    public SimpleWorld setWorldBorderWarningDistance(int warningDistance) {
        world.getWorldBorder().setWarningDistance(warningDistance);
        return this;
    }

    public SimpleWorld setWorldBorderWarningTime(int warningTime) {
        world.getWorldBorder().setWarningTime(warningTime);
        return this;
    }

    public String getGameRuleValue(GameRule<?> rule) {
        return (String) world.getGameRuleValue(rule);
    }

    public <T> SimpleWorld setGameRuleValue(GameRule<T> rule, T value) {
        world.setGameRule(rule, value);
        return this;
    }

    public int getSeaLevel() {
        return world.getSeaLevel();
    }

    public int getMaxHeight() {
        return world.getMaxHeight();
    }

    public boolean getAllowAnimals() {
        return world.getAllowAnimals();
    }

    public SimpleWorld setAllowAnimals(boolean allow) {
        world.setGameRule(GameRule.DO_MOB_SPAWNING, allow);
        return this;
    }
    public boolean getAllowMonsters() {
        return world.getAllowMonsters();
    }

    public SimpleWorld setAllowMonsters(boolean allow) {
        world.setGameRule(GameRule.DO_MOB_SPAWNING, allow);
        return this;
    }

    public SimpleWorld playEffect(Location location, Effect effect, int data) {
        world.playEffect(location, effect, data);
        return this;
    }

    public SimpleWorld playSound(Location location, Sound sound, float volume, float pitch) {
        world.playSound(location, sound, volume, pitch);
        return this;
    }

    public Entity spawnEntity(Location location, EntityType type) {
        return world.spawnEntity(location, type);
    }

    public List<Entity> getEntities() {
        return world.getEntities();
    }

    public List<Player> getPlayers() {
        return world.getPlayers();
    }

    public List<LivingEntity> getLivingEntities() {
        return world.getLivingEntities();
    }

    public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z) {
        return world.getNearbyEntities(location, x, y, z);
    }

    public SimpleWorld strikeLightning(Location location) {
        world.strikeLightning(location);
        return this;
    }

    public SimpleWorld strikeLightningEffect(Location location) {
        world.strikeLightningEffect(location);
        return this;
    }

    public Block getHighestBlockAt(int x, int z) {
        return world.getHighestBlockAt(x, z);
    }

    public int getHighestBlockYAt(int x, int z) {
        return world.getHighestBlockYAt(x, z);
    }

    public SimpleWorld setGameRule(GameRule<Boolean> rule, boolean value) {
        world.setGameRule(rule, value);
        return this;
    }

    public SimpleWorld setGameRule(GameRule<Integer> rule, int value) {
        world.setGameRule(rule, value);
        return this;
    }

    public SimpleWorld setGameRule(GameRule<String> rule, String value) {
        world.setGameRule(rule, value);
        return this;
    }

    public SimpleWorld setSpawnLocation(int x, int y, int z) {
        world.setSpawnLocation(x, y, z);
        return this;
    }

    public Location getSpawnLocation() {
        return world.getSpawnLocation();
    }

    public SimpleWorld setAutoSave(boolean autoSave) {
        world.setAutoSave(autoSave);
        return this;
    }

    public boolean isAutoSave() {
        return world.isAutoSave();
    }

    public SimpleWorld setBiome(int x, int y, int z, Biome biome) {
        world.setBiome(x, y, z, biome);
        return this;
    }

    public Biome getBiome(int x, int y, int z) {
        return world.getBiome(x, y, z);
    }

    public SimpleWorld setPVP(boolean pvp) {
        world.setPVP(pvp);
        return this;
    }

    public boolean isPVP() {
        return world.getPVP();
    }

    public SimpleWorld setKeepSpawnInMemory(boolean keepSpawn) {
        world.setKeepSpawnInMemory(keepSpawn);
        return this;
    }

    public boolean isKeepSpawnInMemory() {
        return world.getKeepSpawnInMemory();
    }
}