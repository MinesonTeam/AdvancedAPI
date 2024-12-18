package kz.hxncus.mc.advancedapi.utility;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Класс для работы с 2D областями в Minecraft
 */
@Data
@ToString
@EqualsAndHashCode
public class BoundingBox2D implements Cloneable, ConfigurationSerializable {
    private double minX;
    private double minZ;
    private double maxX; 
    private double maxZ;
    
    public BoundingBox2D(final double x1, final double z1, final double x2, final double z2) {
        this.resize(x1, z1, x2, z2);
    }

    
    /**
     * Создает новый BoundingBox2D с нулевыми координатами
     */
    public BoundingBox2D() {
        this(0.0D, 0.0D, 0.0D, 0.0D);
    }
    
    /**
     * Проверяет, содержит ли область указанную точку
     */
    public boolean contains(@NonNull final Point2D point) {
        return point.getX() >= this.minX && point.getX() <= this.maxX && 
        point.getZ() >= this.minZ && point.getZ() <= this.maxZ;
    }
    
    /**
     * Расширяет область на указанное расстояние
     */
    public void expand(final double x, final double z) {
        this.minX -= x;
        this.minZ -= z;
        this.maxX += x;
        this.maxZ += z;
    }
    
    /**
     * Сжимает область на указанное расстояние
     */
    public void contract(final double x, final double z) {
        this.minX += x;
        this.minZ += z;
        this.maxX -= x;
        this.maxZ -= z;
    }
    
    /**
     * Перемещает область на указанное расстояние
     */
    public void shift(final double x, final double z) {
        this.minX += x;
        this.minZ += z;
        this.maxX += x;
        this.maxZ += z;
    }
    
    public void shift(@NonNull final Point2D point2d) {
        this.shift(point2d.getX(), point2d.getZ());
    }

    /**
     * Проверяет пересечение с другой областью
     */
    public boolean overlaps(@NonNull final BoundingBox2D other) {
        return this.overlaps(other.minX, other.minZ, other.maxX, other.maxZ);
    }
    
    /**
     * Проверяет пересечение с другой областью
     */
    public boolean overlaps(@NonNull final Point2D firstPoint, @NonNull final Point2D secondPoint) {
        double x1 = firstPoint.getX();
        double z1 = firstPoint.getZ();
        double x2 = secondPoint.getX();
        double z2 = secondPoint.getZ();
        return this.overlaps(Math.min(x1, x2), Math.min(z1, z2), Math.max(x1, x2), Math.max(z1, z2));
    }

    /**
     * Проверяет пересечение с другой областью
     */
    public boolean overlaps(final double minX, final double minZ, final double maxX, final double maxZ) {
        return this.minX < maxX && this.maxX > minX
            && this.minZ < maxZ && this.maxZ > minZ;
    }
    
    /**
     * Получает центр области
     */
    public Point2D getCenter() {
        double x = (minX + maxX) / 2;
        double z = (minZ + maxZ) / 2;
        return new Point2D(x, z);
    }
    
    /**
     * Получает ширину области
     */
    public double getWidth() {
        return maxZ - minZ;
    }
    
    /**
     * Получает длину области
     */
    public double getLength() {
        return maxX - minX;
    }
    
    /**
     * Получает площадь области
     */
    public double getArea() {
        return getWidth() * getLength();
    }
    
    /**
     * Получает периметр области
     */
    public double getPerimeter() {
        return 2 * (getWidth() + getLength());
    }
    
    /**
     * Получает ближайшую точку к указанной локации
     */
    public Point2D getNearestPoint(@NonNull final Point2D point) {
        double x = Math.max(this.minX, Math.min(this.maxX, point.getX()));
        double z = Math.max(this.minZ, Math.min(this.maxZ, point.getZ())); 
        
        return new Point2D(x, z);
    }
    
    /**
     * Получает расстояние до указанной локации
     */
    public double getDistance(@NonNull final Point2D point) {
        return getNearestPoint(point).distance(point);
    }
    
    /**
     * Изменяет размер области
     */
    public BoundingBox2D resize(final double x1, final double z1, final double x2, final double z2) {
        return new BoundingBox2D(x1, z1, x2, z2);
    }
    
    /**
     * Создает новый BoundingBox2D из двух точек
     */
    public static BoundingBox2D of(@NonNull final Point2D first, @NonNull final Point2D second) {
        return new BoundingBox2D(first.getX(), first.getZ(), second.getX(), second.getZ());
    }
    
    /**
     * Создает новый BoundingBox2D из центра и радиуса
     */
    public static BoundingBox2D of(@NonNull final Point2D center, final double radius) {      
        final Point2D first = center.clone().add(-radius, -radius);
        final Point2D second = center.clone().add(radius, radius);
        return new BoundingBox2D(first.getX(), first.getZ(), second.getX(), second.getZ());
    }
    
    public static BoundingBox2D of(@NonNull final Point2D center, final double x, final double z) {
        return new BoundingBox2D(center.getX() - x, center.getZ() - z, center.getX() + x, center.getZ() + z);
    }

    /**
     * Creates a copy of this bounding box.
     *
     * @return the cloned bounding box
     */
    @NonNull
    @Override
    public BoundingBox2D clone() {
        try {
            return (BoundingBox2D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Сериализует область
     */
    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("minX", minX);
        result.put("minZ", minZ);
        result.put("maxX", maxX);
        result.put("maxZ", maxZ);
        return result;
    }

    /**
     * Десериализует область
     */
    @NonNull
    public static BoundingBox2D deserialize(@NonNull Map<String, Object> args) {
        double minX = 0.0D;
        double minZ = 0.0D;
        double maxX = 0.0D;
        double maxZ = 0.0D;

        if (args.containsKey("minX")) {
            minX = Double.parseDouble((String) args.get("minX"));
        }
        if (args.containsKey("minZ")) {
            minZ = Double.parseDouble((String) args.get("minZ"));
        }
        if (args.containsKey("maxX")) {
            maxX = Double.parseDouble((String) args.get("maxX"));
        }
        if (args.containsKey("maxZ")) {
            maxZ = Double.parseDouble((String) args.get("maxZ"));
        }

        return new BoundingBox2D(minX, minZ, maxX, maxZ);
    }
}
