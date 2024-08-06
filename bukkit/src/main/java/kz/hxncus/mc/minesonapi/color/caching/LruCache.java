package kz.hxncus.mc.minesonapi.color.caching;

import lombok.Getter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class LruCache {
	private final Deque<String> QUE = new LinkedList<>();
	
	private final Map<String, LruElement> MAP = new ConcurrentHashMap<>();
	
	private final int maxSize;
	
	public LruCache(final int maxSize) {
		this.maxSize = maxSize;
	}
	
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * 59 + this.maxSize;
		final Object QUE = this.QUE;
		result = result * 59 + ((QUE == null) ? 43 : QUE.hashCode());
		final Object MAP = this.MAP;
		return result * 59 + ((MAP == null) ? 43 : MAP.hashCode());
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o == this)
			return true;
		if (!(o instanceof final LruCache other))
			return false;
		if (!other.canEqual(this))
			return false;
		if (this.maxSize != other.maxSize)
			return false;
		final Object thisQUE = this.QUE;
		final Object otherQUE = other.QUE;
		if (!Objects.equals(thisQUE, otherQUE))
			return false;
		final Object thisMAP = this.MAP;
		final Object otherMAP = other.MAP;
		return Objects.equals(thisMAP, otherMAP);
	}
	
	protected boolean canEqual(final Object other) {
		return other instanceof LruCache;
	}
	
	public String toString() {
		return "LruCache(QUE=" + this.QUE + ", MAP=" + this.MAP + ", maxSize=" + this.maxSize + ")";
	}
	
	public String getResult(final String input) {
		if (input != null && this.MAP.containsKey(input)) {
			final LruElement curr = this.MAP.get(input);
			synchronized (this.QUE) {
				this.QUE.remove(input);
				this.QUE.addFirst(input);
			}
			return curr.result();
		}
		return null;
	}
	
	public void put(final String input, final String result) {
		if (input == null || result == null)
			return;
		synchronized (this.QUE) {
			if (this.MAP.containsKey(input)) {
				this.QUE.remove(input);
			} else {
				final int size = this.QUE.size();
				if (size == this.maxSize && size > 0) {
					final String temp = this.QUE.removeLast();
					this.MAP.remove(temp);
				}
			}
			final LruElement newObj = new LruElement(input, result);
			this.QUE.addFirst(input);
			this.MAP.put(input, newObj);
		}
	}
}
