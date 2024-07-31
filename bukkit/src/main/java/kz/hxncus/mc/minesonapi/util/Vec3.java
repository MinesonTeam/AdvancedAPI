package kz.hxncus.mc.minesonapi.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.FastMath;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@Setter
@Getter
@AllArgsConstructor
@Builder(toBuilder=true)
public class Vec3 {
    private double x;
    private double y;
    private double z;

    public Vec3(Location loc) {
        this(loc.getX(), loc.getY(), loc.getZ());
    }

    public Vec3(Vector vec) {
        this(vec.getX(), vec.getY(), vec.getZ());
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public Location toLocation(World world, float yaw, float pitch) {
        return new Location(world, this.x, this.y, this.z, yaw, pitch);
    }

    public double distance(Vec3 vec) {
        return FastMath.sqrt(pow2(this.x - vec.x) + pow2(this.y - vec.y) + pow2(this.z - vec.z));
    }

    private double pow2(double val) {
        return val * val;
    }

    public Vec3 norm() {
        double length = length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return this;
    }

    public Vec3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3 add(Vec3 vec) {
        return add(vec.x, vec.y, vec.z);
    }

    public Vec3 subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3 subtract(Vec3 vec) {
        return subtract(vec.x, vec.y, vec.z);
    }

    public Vec3 divide(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vec3 divide(Vec3 vec) {
        return divide(vec.x, vec.y, vec.z);
    }

    public Vec3 multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vec3 multiply(Vec3 vec) {
        return multiply(vec.x, vec.y, vec.z);
    }

    public Vec3 multiply(double mult) {
        return multiply(mult, mult, mult);
    }

    public double scalar(Vec3 vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    public double angle(Vec3 vec) {
        return FastMath.acos(scalar(vec) / length() * vec.length());
    }

    public double length() {
        return FastMath.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vec3 cross(Vec3 vec) {
        return new Vec3(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Vec3 rotateX(double sin, double cos) {
        double prevY = this.y;
        this.y = prevY * cos - this.z * sin;
        this.z = prevY * sin + this.z * cos;
        return this;
    }

    public Vec3 rotateY(double sin, double cos) {
        double prevX = this.x;
        this.x = prevX * cos + this.z * sin;
        this.z = this.z * cos - prevX * sin;
        return this;
    }

    public Vec3 rotateZ(double sin, double cos) {
        double prevX = this.x;
        this.y = prevX * sin + this.y * cos;
        this.x = prevX * cos - this.y * sin;
        return this;
    }

    public Vec3 rotateZ(double angle) {
        double cos = FastMath.cos(angle);
        double sin = FastMath.sin(angle);
        return rotateZ(sin, cos);
    }

    public Vec3 rotateY(double angle) {
        double cos = FastMath.cos(angle);
        double sin = FastMath.sin(angle);
        return rotateY(sin, cos);
    }

    public Vec3 rotateX(double angle) {
        double cos = FastMath.cos(angle);
        double sin = FastMath.sin(angle);
        return rotateX(sin, cos);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vec3 vec3 = (Vec3)o;
        return (Double.compare(vec3.x, this.x) == 0 && Double.compare(vec3.y, this.y) == 0 && Double.compare(vec3.z, this.z) == 0);
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.x);
        int result = (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.z);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        return result;
    }

    public String toString() {
        return "Vec3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }

    public Vec3 copy() {
        return this.toBuilder().build();
    }
}
