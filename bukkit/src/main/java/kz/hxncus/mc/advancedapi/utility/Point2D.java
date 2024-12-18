package kz.hxncus.mc.advancedapi.utility;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Класс для работы с 2D точками в Minecraft
 */
@Data
@ToString
@EqualsAndHashCode
public class Point2D implements Cloneable, ConfigurationSerializable {
    private double x;
    private double z;
    
    /**
     * Создает точку из координат
     */
    public Point2D(double x, double z) {
        this.x = x;
        this.z = z;
    }
    
    /**
     * Создает точку из локации Minecraft
     */
    public static Point2D fromLocation(@NonNull final Location location) {
        return new Point2D(location.getX(), location.getZ());
    }
    
    /**
     * Конвертирует в локацию Minecraft
     */
    public Location toLocation(@NonNull final World world, final double y) {
        return new Location(world, this.x, y, this.z);
    }
    
    /**
     * Вычисляет расстояние до другой точки
     */
    public double distance(@NonNull final Point2D other) {
        final double dx = this.x - other.x;
        final double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dz * dz);
    }
    
    /**
     * Вычисляет квадрат расстояния (для оптимизации)
     */
    public double distanceSquared(@NonNull Point2D other) {
        final double dx = this.x - other.x;
        final double dz = this.z - other.z;
        return dx * dx + dz * dz;
    }
    
    /**
     * Добавляет координаты
     */
    public Point2D add(final double x, final double z) {
        this.x += x;
        this.z += z;
        return this;
    }
    
    /**
     * Добавляет другую точку
     */
    public Point2D add(@NonNull Point2D other) {
        return add(other.x, other.z);
    }
    
    /**
     * Вычитает координаты
     */
    public Point2D subtract(final double x, final double z) {
        this.x -= x;
        this.z -= z;
        return this;
    }
    
    /**
     * Вычитает другую точку
     */
    public Point2D subtract(@NonNull Point2D other) {
        return subtract(other.x, other.z);
    }
    
    /**
     * Умножает на скаляр
     */
    public Point2D multiply(final double scalar) {
        this.x *= scalar;
        this.z *= scalar;
        return this;
    }
    
    /**
     * Нормализует вектор
     */
    public Point2D normalize() {
        final double length = Math.sqrt(this.x * this.x + this.z * this.z);

        this.x /= length;
        this.z /= length;

        return this;
    }
    
    /**
     * Вычисляет длину вектора
     */
    public double length() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }
    
    /**
     * Вычисляет угол между векторами
     */
    public double angle(@NonNull final Point2D other) {
        final double dot = this.x * other.x + this.z * other.z;
        return Math.acos(dot / (length() * other.length()));
    }
    
    /**
     * Проверяет, находится ли точка в прямоугольнике
     */
    public boolean contains(@NonNull BoundingBox2D boundingBox) {
        return boundingBox.contains(this);
    }
    
    /**
     * Вычисляет средняя точка
     */
    public Point2D midpoint(@NonNull final Point2D other) {
        return new Point2D(
            (this.x + other.x) / 2,
            (this.z + other.z) / 2
        );
    }
    
    /**
     * Поворачивает точку на угол (в радианах)
     */
    public Point2D rotate(final double angleRad) {
        final double cos = Math.cos(angleRad);
        final double sin = Math.sin(angleRad);
        return new Point2D(
            this.x * cos - this.z * sin,
            this.x * sin + this.z * cos
        );
    }

    /**
     * Creates a copy of this point.
     *
     * @return the cloned point
     */
    @NonNull
    @Override
    public Point2D clone() {
        try {
            return (Point2D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Сериализует точку
     */
    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("x", this.x);
        result.put("z", this.z);
        return result;
    }

    /**
     * Десериализует точку
     */
    @NonNull
    public static Point2D deserialize(@NonNull Map<String, Object> args) {
        double x = 0.0D;
        double z = 0.0D;

        if (args.containsKey("x")) {
            x = Double.parseDouble((String) args.get("x"));
        }
        if (args.containsKey("z")) {
            z = Double.parseDouble((String) args.get("z"));
        }
        return new Point2D(x, z);
    }
}
