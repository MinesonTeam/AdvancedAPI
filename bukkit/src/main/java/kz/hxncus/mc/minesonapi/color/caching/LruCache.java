package kz.hxncus.mc.minesonapi.color.caching;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Lru cache.
 *
 * @author Hxncus
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class LruCache {
	private final Deque<String> deque = new LinkedList<>();
	
	private final Map<String, LruElement> map = new ConcurrentHashMap<>(16);
	
	private final int maxSize;
	
	/**
	 * Instantiates a new Lru cache.
	 *
	 * @param maxSize the max size
	 */
	public LruCache(final int maxSize) {
		this.maxSize = maxSize;
	}
	
	/**
	 * Can equal boolean.
	 *
	 * @param other the other
	 * @return the boolean
	 */
	protected boolean canEqual(final Object other) {
		return other instanceof LruCache;
	}
	
	/**
	 * Gets result.
	 *
	 * @param input the input
	 * @return the result
	 */
	public String getResult(final String input) {
		if (input != null && this.map.containsKey(input)) {
			final LruElement curr = this.map.get(input);
			synchronized (this.deque) {
				this.deque.remove(input);
				this.deque.addFirst(input);
			}
			return curr.result();
		}
		return null;
	}
	
	/**
	 * Put.
	 *
	 * @param input  the input
	 * @param result the result
	 */
	public void put(final String input, final String result) {
		if (input == null || result == null)
			return;
		synchronized (this.deque) {
			if (this.map.containsKey(input)) {
				this.deque.remove(input);
			} else {
				final int size = this.deque.size();
				if (size == this.maxSize && size > 0) {
					final String temp = this.deque.removeLast();
					this.map.remove(temp);
				}
			}
			final LruElement newObj = new LruElement(input, result);
			this.deque.addFirst(input);
			this.map.put(input, newObj);
		}
	}
}
