package kz.hxncus.mc.advancedapi.api.bukkit.region;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import com.google.common.base.Optional;

import lombok.NonNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Интерфейс для работы с регионами
 */
public interface Region extends ConfigurationSerializable {
    /**
     * Получить уникальный идентификатор региона
     */
    @NonNull UUID getUniqueId();
    
    /**
     * Получить название региона
     */
    @NonNull String getName();
    
    /**
     * Установить название региона
     */
    void setName(@NonNull String name);
    
    /**
     * Получить владельца региона
     */
    @NonNull Optional<UUID> getOwner();

    /**
     * Установить владельца региона
     */
    void setOwner(@NonNull UUID owner);
    
    /**
     * Получить приоритет региона
     */
    int getPriority();
    
    /**
     * Установить приоритет региона
     */
    void setPriority(int priority);
    
    /**
     * Получить мир региона
     */
    @NonNull World getWorld();
    
    /**
     * Установить мир региона
     */
    void setWorld(@NonNull World world);
    
    /**
     * Получить границы региона
     */
    @NonNull BoundingBox getBoundingBox();
    
    /**
     * Получить первую точку региона
     */
    default @NonNull Vector getMinPoint() {
        return this.getBoundingBox().getMin();
    }
    
    /**
     * Получить вторую точку региона
     */
    default @NonNull Vector getMaxPoint() {
        return this.getBoundingBox().getMax();
    }

    /**
     * Получает ближайшую точку к указанной локации
     */
    default Vector getNearestPoint(final double x, final double y, final double z) {
        double closestX = Math.min(Math.max(x, this.getBoundingBox().getMinX()), this.getBoundingBox().getMaxX());    
        double closestY = Math.min(Math.max(y, this.getBoundingBox().getMinY()), this.getBoundingBox().getMaxY());
        double closestZ = Math.min(Math.max(z, this.getBoundingBox().getMinZ()), this.getBoundingBox().getMaxZ());
        return new Vector(closestX, closestY, closestZ);
    }

    /**
     * Получает ближайшую точку к указанной точке
     */
    default Vector getNearestPoint(@NonNull Vector vector) {
        return this.getNearestPoint(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Получает ближайшую точку к указанной локации
     */
    default Vector getNearestPoint(@NonNull Location location) {
        return this.getNearestPoint(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Получает расстояние до указанной точки
     */
    default double getDistance(final double x, final double y, final double z) {
        return this.getNearestPoint(x, y, z).distance(new Vector(x, y, z));
    }
    
    /**
     * Получает расстояние до указанной локации
     */
    default double getDistance(@NonNull Location location) {
        return this.getDistance(location.getX(), location.getY(), location.getZ());
    }
    /**
     * Проверить, находится ли другая область в регионе
     */
    default boolean contains(@NonNull BoundingBox boundingBox) {
        return this.getBoundingBox().contains(boundingBox);
    }
    
    /**
     * Проверить, находится ли точка в регионе
     */
    default boolean contains(final double x, final double y, final double z) {
        return this.getBoundingBox().contains(x, y, z);
    }
    
    /**
     * Проверить, находится ли локация в регионе
     */
    default boolean contains(@NonNull Vector vector) {
        return this.contains(vector.getX(), vector.getY(), vector.getZ());
    }
    
    /**
     * Проверить, находится ли локация в регионе
     */
    default boolean contains(@NonNull Location location) {
        return this.contains(location.getX(), location.getY(), location.getZ());
    }
    
    /**
     * Проверить, находится ли игрок в регионе
     */
    default boolean contains(@NonNull Entity entity) {
        return this.contains(entity.getLocation());
    }
    
    /**
     * Получить центр региона
     */
    default @NonNull Vector getCenter() {
        return this.getBoundingBox().getCenter();
    }
    
    /**
     * Получить объем региона
     */
    default double getVolume() {
        return this.getBoundingBox().getVolume();
    }
    
    /**
     * Получает высоту региона
     */
    default double getHeight() {
        return this.getBoundingBox().getHeight();
    }
    
    /**
     * Получает ширину региона
     */
    default double getWidthX() {
        return this.getBoundingBox().getWidthX();
    }
    
    /**
     * Получает длину региона
     */
    default double getWidthZ() {
        return this.getBoundingBox().getWidthZ();
    }
    
    /**
     * Получает список игроков, находящихся в области
     */
    default @NonNull List<Player> getPlayers() {
        return this.getWorld().getPlayers().stream()
            .filter(this::contains)
            .collect(Collectors.toList());
    }
    
    /**
     * Получает список сущностей, находящихся в области
     */
    default @NonNull List<Entity> getEntities() {
        return this.getWorld().getEntities().stream()
            .filter(this::contains)
            .collect(Collectors.toList());
    }
}
