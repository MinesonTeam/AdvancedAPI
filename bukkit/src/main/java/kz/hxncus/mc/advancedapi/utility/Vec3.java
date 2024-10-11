package kz.hxncus.mc.advancedapi.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * The type Vec 3.
 * @author Hxncus
 * @since  1.0.1
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Vec3 {
	private double x;
	private double y;
	private double z;
	
	/**
	 * Instantiates a new Vec 3.
	 *
	 * @param loc the loc
	 */
	public Vec3(final Location loc) {
		this(loc.getX(), loc.getY(), loc.getZ());
	}
	
	/**
	 * Instantiates a new Vec 3.
	 *
	 * @param vec the vec
	 */
	public Vec3(final Vector vec) {
		this(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * To vector vector.
	 *
	 * @return the vector
	 */
	public Vector toVector() {
		return new Vector(this.x, this.y, this.z);
	}
	
	/**
	 * To location.
	 *
	 * @param world the world
	 * @return the location
	 */
	public Location toLocation(final World world) {
		return new Location(world, this.x, this.y, this.z);
	}
	
	/**
	 * To location.
	 *
	 * @param world the world
	 * @param yaw   the yaw
	 * @param pitch the pitch
	 * @return the location
	 */
	public Location toLocation(final World world, final float yaw, final float pitch) {
		return new Location(world, this.x, this.y, this.z, yaw, pitch);
	}
	
	/**
	 * Distance double.
	 *
	 * @param vec the vec
	 * @return the double
	 */
	public double distance(final Vec3 vec) {
		return Math.sqrt(this.pow2(this.x - vec.getX()) + this.pow2(this.y - vec.getY()) + this.pow2(this.z - vec.getZ()));
	}
	
	private double pow2(final double val) {
		return val * val;
	}
	
	/**
	 * Norm vec 3.
	 *
	 * @return the vec 3
	 */
	public Vec3 norm() {
		final double length = this.length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}
	
	/**
	 * Length double.
	 *
	 * @return the double
	 */
	public double length() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	/**
	 * Add vec 3.
	 *
	 * @param vec the vec
	 * @return the vec 3
	 */
	public Vec3 add(final Vec3 vec) {
		return this.add(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Add vec 3.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the vec 3
	 */
	public Vec3 add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Subtract vec 3.
	 *
	 * @param vec the vec
	 * @return the vec 3
	 */
	public Vec3 subtract(final Vec3 vec) {
		return this.subtract(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Subtract vec 3.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the vec 3
	 */
	public Vec3 subtract(final double x, final double y, final double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	/**
	 * Divide vec 3.
	 *
	 * @param vec the vec
	 * @return the vec 3
	 */
	public Vec3 divide(final Vec3 vec) {
		return this.divide(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Divide vec 3.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the vec 3
	 */
	public Vec3 divide(final double x, final double y, final double z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	/**
	 * Multiply vec 3.
	 *
	 * @param vec the vec
	 * @return the vec 3
	 */
	public Vec3 multiply(final Vec3 vec) {
		return this.multiply(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Multiply vec 3.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the vec 3
	 */
	public Vec3 multiply(final double x, final double y, final double z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	/**
	 * Multiply vec 3.
	 *
	 * @param multiplier the multiplier
	 * @return the vec 3
	 */
	public Vec3 multiply(final double multiplier) {
		return this.multiply(multiplier, multiplier, multiplier);
	}
	
	/**
	 * Angle double.
	 *
	 * @param vec the vec
	 * @return the double
	 */
	public double angle(final Vec3 vec) {
		return StrictMath.acos(this.scalar(vec) / this.length() * vec.length());
	}
	
	/**
	 * Scalar double.
	 *
	 * @param vec the vec
	 * @return the double
	 */
	public double scalar(final Vec3 vec) {
		return this.x * vec.getX() + this.y * vec.getY() + this.z * vec.getZ();
	}
	
	/**
	 * Cross vec 3.
	 *
	 * @param vec the vec
	 * @return the vec 3
	 */
	public Vec3 cross(final Vec3 vec) {
		return new Vec3(this.y * vec.getZ() - this.z * vec.getY(), this.z * vec.getX() - this.x * vec.getZ(), this.x * vec.getY() - this.y * vec.getX());
	}
	
	/**
	 * Rotate z vec 3.
	 *
	 * @param angle the angle
	 * @return the vec 3
	 */
	public Vec3 rotateZ(final double angle) {
		final double cos = StrictMath.cos(angle);
		final double sin = StrictMath.sin(angle);
		return this.rotateZ(sin, cos);
	}
	
	/**
	 * Rotate z vec 3.
	 *
	 * @param sin the sin
	 * @param cos the cos
	 * @return the vec 3
	 */
	public Vec3 rotateZ(final double sin, final double cos) {
		final double prevX = this.x;
		this.y = prevX * sin + this.y * cos;
		this.x = prevX * cos - this.y * sin;
		return this;
	}
	
	/**
	 * Rotate y vec 3.
	 *
	 * @param angle the angle
	 * @return the vec 3
	 */
	public Vec3 rotateY(final double angle) {
		final double cos = StrictMath.cos(angle);
		final double sin = StrictMath.sin(angle);
		return this.rotateY(sin, cos);
	}
	
	/**
	 * Rotate y vec 3.
	 *
	 * @param sin the sin
	 * @param cos the cos
	 * @return the vec 3
	 */
	public Vec3 rotateY(final double sin, final double cos) {
		final double prevX = this.x;
		this.x = prevX * cos + this.z * sin;
		this.z = this.z * cos - prevX * sin;
		return this;
	}
	
	/**
	 * Rotate x vec 3.
	 *
	 * @param angle the angle
	 * @return the vec 3
	 */
	public Vec3 rotateX(final double angle) {
		final double cos = StrictMath.cos(angle);
		final double sin = StrictMath.sin(angle);
		return this.rotateX(sin, cos);
	}
	
	/**
	 * Rotate x vec 3.
	 *
	 * @param sin the sin
	 * @param cos the cos
	 * @return the vec 3
	 */
	public Vec3 rotateX(final double sin, final double cos) {
		final double prevY = this.y;
		this.y = prevY * cos - this.z * sin;
		this.z = prevY * sin + this.z * cos;
		return this;
	}
	
	/**
	 * Copy vec 3.
	 *
	 * @return the vec 3
	 */
	public Vec3 copy() {
		return this.toBuilder()
		           .build();
	}
}
