package kz.hxncus.mc.minesonapi.util.tuples;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class Pair.
 *
 * @param <L> the type parameter
 * @param <R> the type parameter
 * @author Hxncus
 * @since  1.0.1
 */
@Data
@AllArgsConstructor
public class Pair<L, R> {
	private L left;
	private R right;
}
