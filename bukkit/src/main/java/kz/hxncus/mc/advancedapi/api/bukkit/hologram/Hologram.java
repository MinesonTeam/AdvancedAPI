package kz.hxncus.mc.advancedapi.api.bukkit.hologram;

import kz.hxncus.mc.advancedapi.utility.HologramUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

public interface Hologram extends ConfigurationSerializable {
    String getName();
    void setName(final String name);
    Location getLocation();
    void setLocation(final Location location);
    boolean isSpawned();
    void setSpawned(boolean spawned);
    List<String> getLines();
    void setLines(List<String> lines);
    List<ArmorStand> getEntities();

    default void addLine(final String line) {
        this.getLines().add(line);
        this.updateIfSpawned();
    }

    default void addLines(final List<String> lines) {
        this.getLines().addAll(lines);
        this.updateIfSpawned();
    }

    default void addLine(final int index, final String line) {
        this.getLines().add(index, line);
        this.updateIfSpawned();
    }

    default void setLine(final int index, final String line) {
        this.getLines().set(index, line);
        this.updateIfSpawned();
    }

    default void removeLine(final String line) {
        this.getLines().remove(line);
        this.updateIfSpawned();
    }

    default void removeLines(final List<String> lines) {
        this.getLines().removeAll(lines);
        this.updateIfSpawned();
    }

    default void move(final Location location) {
        this.setLocation(location);
        if (!this.isSpawned()) {
            return;
        }
        Location spawnLocation = location.clone();
        List<ArmorStand> entities = this.getEntities();
        for (ArmorStand entity : entities) {
            spawnLocation.add(0, HologramUtil.LINE_HEIGHT, 0);
            entity.teleport(spawnLocation);
        }
    }
    
    default void spawn() {
        if (this.isSpawned()) {
            return;
        }
        Location location = this.getLocation();
        Chunk cnk = location.getChunk();
        if (!cnk.isLoaded()) {
            cnk.load();
        }
        this.getEntities().forEach(Entity::remove);
        this.getEntities().clear();
        Location spawnLocation = location.clone();
        for (int i = 0; i < this.getLines().size(); i++) {
            spawnLocation = spawnLocation.add(0, HologramUtil.LINE_HEIGHT, 0);
            ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(spawnLocation, EntityType.ARMOR_STAND);
            entity.setGravity(false);
            entity.setBasePlate(false);
            entity.setVisible(false);
            entity.setCustomNameVisible(true);
            entity.setCustomName(this.getLines().get(i));
            this.getEntities().add(entity);
        }
        this.setSpawned(true);
    }
    
    default void despawn() {
        if (!this.isSpawned()) {
            return;
        }
        this.getEntities().forEach(Entity::remove);
        this.getEntities().clear();
        this.setSpawned(false);
    }

    default void update() {
        despawn();
        spawn();
    }

    default void updateIfSpawned() {
        if (this.isSpawned()) {
            update();
        }
    }
}
