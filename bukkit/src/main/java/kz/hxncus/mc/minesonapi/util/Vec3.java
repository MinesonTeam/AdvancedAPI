package kz.hxncus.mc.minesonapi.util;

import lombok.*;
import org.apache.commons.math3.util.FastMath;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@Setter
@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public class Vec3 {
	private double x;
	private double y;
	private double z;
	
	public Vec3(final Location loc) {
		this(loc.getX(), loc.getY(), loc.getZ());
	}
	
	public Vec3(final Vector vec) {
		this(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public Vector toVector() {
		return new Vector(this.x, this.y, this.z);
	}
	
	public Location toLocation(final World world) {
		return new Location(world, this.x, this.y, this.z);
	}
	
	public Location toLocation(final World world, final float yaw, final float pitch) {
		return new Location(world, this.x, this.y, this.z, yaw, pitch);
	}
	
	public double distance(final Vec3 vec) {
		return Math.sqrt(this.pow2(this.x - vec.x) + this.pow2(this.y - vec.y) + this.pow2(this.z - vec.z));
	}
	
	private double pow2(final double val) {
		return val * val;
	}
	
	public Vec3 norm() {
		final double length = this.length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}
	
	public double length() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public Vec3 add(final Vec3 vec) {
		return this.add(vec.x, vec.y, vec.z);
	}
	
	public Vec3 add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public Vec3 subtract(final Vec3 vec) {
		return this.subtract(vec.x, vec.y, vec.z);
	}
	
	public Vec3 subtract(final double x, final double y, final double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public Vec3 divide(final Vec3 vec) {
		return this.divide(vec.x, vec.y, vec.z);
	}
	
	public Vec3 divide(final double x, final double y, final double z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	public Vec3 multiply(final Vec3 vec) {
		return this.multiply(vec.x, vec.y, vec.z);
	}
	
	public Vec3 multiply(final double x, final double y, final double z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	public Vec3 multiply(final double mult) {
		return this.multiply(mult, mult, mult);
	}
	
	public double angle(final Vec3 vec) {
		return FastMath.acos(this.scalar(vec) / this.length() * vec.length());
	}
	
	public double scalar(final Vec3 vec) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}
	
	public Vec3 cross(final Vec3 vec) {
		return new Vec3(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
	}
	
	public Vec3 rotateZ(final double angle) {
		final double cos = FastMath.cos(angle);
		final double sin = FastMath.sin(angle);
		return this.rotateZ(sin, cos);
	}
	
	public Vec3 rotateZ(final double sin, final double cos) {
		final double prevX = this.x;
		this.y = prevX * sin + this.y * cos;
		this.x = prevX * cos - this.y * sin;
		return this;
	}
	
	public Vec3 rotateY(final double angle) {
		final double cos = FastMath.cos(angle);
		final double sin = FastMath.sin(angle);
		return this.rotateY(sin, cos);
	}
	
	public Vec3 rotateY(final double sin, final double cos) {
		final double prevX = this.x;
		this.x = prevX * cos + this.z * sin;
		this.z = this.z * cos - prevX * sin;
		return this;
	}
	
	public Vec3 rotateX(final double angle) {
		final double cos = FastMath.cos(angle);
		final double sin = FastMath.sin(angle);
		return this.rotateX(sin, cos);
	}
	
	public Vec3 rotateX(final double sin, final double cos) {
		final double prevY = this.y;
		this.y = prevY * cos - this.z * sin;
		this.z = prevY * sin + this.z * cos;
		return this;
	}
	
	public Vec3 copy() {
		return this.toBuilder()
		           .build();
	}
}
