package kz.hxncus.mc.minesonapi.utility.tuples;

import lombok.*;

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
@EqualsAndHashCode(callSuper = false)
public class Triplet<L, M, R> extends Pair<L, R> {
	protected M middle;
	
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
	
	@NonNull
	public Triplet<R, M, L> swap() {
		return Triplet.of(this.right, this.middle, this.left);
	}
	
	public static <L, M, R> Triplet<L, M, R> of(@NonNull L left, @NonNull M middle, @NonNull R right) {
		return new Triplet<>(left, middle, right);
	}
}
