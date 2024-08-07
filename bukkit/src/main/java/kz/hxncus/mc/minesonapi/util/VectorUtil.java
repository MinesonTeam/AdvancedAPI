package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.util.tuples.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/**
 * The type Vector util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class VectorUtil {
	/**
	 * The Zero vector.
	 */
	public final Vector ZERO_VECTOR = new Vector();
	
	/**
	 * Gets min and max vector.
	 *
	 * @param firstBlock  the first block
	 * @param secondBlock the second block
	 * @return the min and max vector
	 */
	public Pair<Vector, Vector> getMinAndMaxVector(final Block firstBlock, final Block secondBlock) {
		final BoundingBox boundingBox = BoundingBox.of(firstBlock, secondBlock);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	/**
	 * Gets min and max vector.
	 *
	 * @param firstLoc  the first loc
	 * @param secondLoc the second loc
	 * @return the min and max vector
	 */
	public Pair<Vector, Vector> getMinAndMaxVector(final Location firstLoc, final Location secondLoc) {
		final BoundingBox boundingBox = BoundingBox.of(firstLoc, secondLoc);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	/**
	 * Gets min and max vector.
	 *
	 * @param firstVector  the first vector
	 * @param secondVector the second vector
	 * @return the min and max vector
	 */
	public Pair<Vector, Vector> getMinAndMaxVector(final Vector firstVector, final Vector secondVector) {
		final BoundingBox boundingBox = BoundingBox.of(firstVector, secondVector);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	/**
	 * Gets min and max vector.
	 *
	 * @param location the location
	 * @param x        the x
	 * @param y        the y
	 * @param z        the z
	 * @return the min and max vector
	 */
	public Pair<Vector, Vector> getMinAndMaxVector(final Location location, final double x, final double y, final double z) {
		final BoundingBox boundingBox = BoundingBox.of(location, x, y, z);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	/**
	 * Gets min and max vector.
	 *
	 * @param vector the vector
	 * @param x      the x
	 * @param y      the y
	 * @param z      the z
	 * @return the min and max vector
	 */
	public Pair<Vector, Vector> getMinAndMaxVector(final Vector vector, final double x, final double y, final double z) {
		final BoundingBox boundingBox = BoundingBox.of(vector, x, y, z);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
}
