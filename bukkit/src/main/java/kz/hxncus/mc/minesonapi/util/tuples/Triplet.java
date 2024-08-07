package kz.hxncus.mc.minesonapi.util.tuples;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class Triplet.
 *
 * @param <L> the type parameter
 * @param <M> the type parameter
 * @param <R> the type parameter
 * @author Hxncus
 * @since  1.0.1
 */
@Getter
@Setter
@ToString
public class Triplet<L, M, R> extends Pair<L, R> {
	private final M middle;
	
	/**
	 * Instantiates a new Triplet.
	 *
	 * @param left   the left
	 * @param middle the middle
	 * @param right  the right
	 */
	public Triplet(final L left, final M middle, final R right) {
		super(left, right);
		this.middle = middle;
	}
}
