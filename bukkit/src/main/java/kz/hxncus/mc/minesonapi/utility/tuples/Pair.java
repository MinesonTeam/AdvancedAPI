package kz.hxncus.mc.minesonapi.utility.tuples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

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
public class Pair<L, R> implements Serializable {
	protected L left;
	protected R right;
	
	public L getKey() {
		return left;
	}
	
	public R getValue() {
		return right;
	}
	
	public L getFirst() {
		return left;
	}
	
	public R getSecond() {
		return right;
	}
	
	public void setFirst(L first) {
		this.left = first;
	}
	
	public void setSecond(R second) {
		this.right = second;
	}
	
	@NonNull
	public Pair<R, L> swap() {
		return Pair.of(this.right, this.left);
	}
	
	public static <L, R> Pair<L, R> of(@NonNull L left, @NonNull R right) {
		return new Pair<>(left, right);
	}
}
