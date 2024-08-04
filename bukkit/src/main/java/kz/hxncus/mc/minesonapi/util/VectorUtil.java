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

    public Pair<Vector, Vector> getMinAndMaxVector(Block firstBlock, Block secondBlock) {
        BoundingBox boundingBox = BoundingBox.of(firstBlock, secondBlock);
        return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
    }

    public Pair<Vector, Vector> getMinAndMaxVector(Location firstLoc, Location secondLoc) {
        BoundingBox boundingBox = BoundingBox.of(firstLoc, secondLoc);
        return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
    }

    public Pair<Vector, Vector> getMinAndMaxVector(Vector firstVector, Vector secondVector) {
        BoundingBox boundingBox = BoundingBox.of(firstVector, secondVector);
        return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
    }

    public Pair<Vector, Vector> getMinAndMaxVector(Location location, double x, double y, double z) {
        BoundingBox boundingBox = BoundingBox.of(location, x, y, z);
        return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
    }

    public Pair<Vector, Vector> getMinAndMaxVector(Vector vector, double x, double y, double z) {
        BoundingBox boundingBox = BoundingBox.of(vector, x, y, z);
        return new Pair<>(boundingBox.getMin(), boundingBox.getMax());
    }
}
