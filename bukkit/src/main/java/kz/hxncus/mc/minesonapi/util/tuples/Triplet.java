package kz.hxncus.mc.minesonapi.util.tuples;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Triplet<L, M, R> extends Pair<L, R> {
	private final M middle;
	
	public Triplet(final L left, final M middle, final R right) {
		super(left, right);
		this.middle = middle;
	}
}
