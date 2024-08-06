package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.util.tuples.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

@UtilityClass
public class VectorUtil {
	public final Vector ZERO_VECTOR = new Vector();
	
	public Pair<Vector, Vector> getMinAndMaxVector(final Block firstBlock, final Block secondBlock) {
		final BoundingBox boundingBox = BoundingBox.of(firstBlock, secondBlock);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	public Pair<Vector, Vector> getMinAndMaxVector(final Location firstLoc, final Location secondLoc) {
		final BoundingBox boundingBox = BoundingBox.of(firstLoc, secondLoc);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	public Pair<Vector, Vector> getMinAndMaxVector(final Vector firstVector, final Vector secondVector) {
		final BoundingBox boundingBox = BoundingBox.of(firstVector, secondVector);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	public Pair<Vector, Vector> getMinAndMaxVector(final Location location, final double x, final double y, final double z) {
		final BoundingBox boundingBox = BoundingBox.of(location, x, y, z);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
	
	public Pair<Vector, Vector> getMinAndMaxVector(final Vector vector, final double x, final double y, final double z) {
		final BoundingBox boundingBox = BoundingBox.of(vector, x, y, z);
		return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
	}
}
