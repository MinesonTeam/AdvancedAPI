package kz.hxncus.mc.advancedapi.api.caching;

import kz.hxncus.mc.advancedapi.utility.tuples.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public abstract class LocalCache<K, V> extends HashMap<K, V> implements ConcurrentMap<K, V> {
	// Left value is expireAfterAccessNanos
	// Right value is expireAfterWriteNanos
	private final ConcurrentMap<K, Pair<Long, Long>> expireNanosMap = new ConcurrentHashMap<>();
	private final long expireAfterAccessNanos;
	private final long expireAfterWriteNanos;
	
	protected LocalCache(long expireAfterAccessNanos, long expireAfterWriteNanos) {
		this.expireAfterAccessNanos = expireAfterAccessNanos;
		this.expireAfterWriteNanos = expireAfterWriteNanos;
	}
	
	protected LocalCache() {
		this(0L, 0L);
	}
	
	protected LocalCache(final long expireAfterAccess, final long expireAfterWrite, TimeUnit timeUnit) {
		this(timeUnit.toNanos(expireAfterAccess), timeUnit.toNanos(expireAfterWrite));
	}
	
	@Override
	public V get(Object key) {
		Pair<Long, Long> pair = this.expireNanosMap.get(key);
		if (pair == null) {
			return null;
		}
		long now = System.nanoTime();
		if (this.removeIfExpired(key, pair, now)) {
			return null;
		}
		pair.setLeft(now);
		return super.get(key);
	}
	
	@Override
	public V put(K key, V value) {
		long now = System.nanoTime();
		long expireAfterAccess = this.isExpiresAfterAccess() ? now : 0L;
		long expireAfterWrite = this.isExpiresAfterWrite() ? now : 0L;
		this.expireNanosMap.put(key, new Pair<>(expireAfterAccess, expireAfterWrite));
		return super.put(key, value);
	}
	
	@Override
	public boolean isEmpty() {
		this.removeAllExpired(System.nanoTime());
		return super.isEmpty();
	}
	
	private void removeAllExpired(long now) {
		for (Entry<K, Pair<Long, Long>> entry : this.expireNanosMap.entrySet()) {
			this.removeIfExpired(entry.getKey(), entry.getValue(), System.nanoTime());
		}
	}
	
	@Override
	public boolean containsValue(final Object value) {
		this.removeAllExpired(System.nanoTime());
		return super.containsValue(value);
	}
	
	@Override
	public boolean containsKey(final Object key) {
		if (this.removeIfExpired(key, System.nanoTime())) {
			return false;
		}
		return super.containsKey(key);
	}
	
	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public void clear() {
		this.expireNanosMap.clear();
		super.clear();
	}
	
	@Override
	public Set<K> keySet() {
		this.removeAllExpired(System.nanoTime());
		return super.keySet();
	}
	
	@Override
	public Collection<V> values() {
		this.removeAllExpired(System.nanoTime());
		return super.values();
	}
	
	@Override
	public V putIfAbsent(final K key, final V value) {
		this.removeAllExpired(System.nanoTime());
		this.setExpiresAfterWrite(key, System.nanoTime());
		return super.putIfAbsent(key, value);
	}
	
	@Override
	public V remove(final Object key) {
		this.expireNanosMap.remove(key);
		return super.remove(key);
	}
	
	@Override
	public boolean remove(final Object key, final Object value) {
		this.expireNanosMap.remove(key);
		return super.remove(key, value);
	}
	
	@Override
	public boolean replace(final K key, final V oldValue, final V newValue) {
		boolean replaced = super.replace(key, oldValue, newValue);
		if (replaced) {
			this.setExpiresAfterWrite(key, System.nanoTime());
		}
		return replaced;
	}
	
	@Override
	public V replace(final K key, final V value) {
		V replaced = super.replace(key, value);
		if (replaced != null) {
			this.setExpiresAfterWrite(key, System.nanoTime());
		}
		return replaced;
	}
	
	private boolean removeIfExpired(Object key, long now) {
		Pair<Long, Long> pair = this.expireNanosMap.get(key);
		if (pair == null) {
			return false;
		}
		return this.removeIfExpired(key, pair, now);
	}
	
	private boolean removeIfExpired(Object key, Pair<Long, Long> pair, long now) {
		if (this.isExpired(pair, now)) {
			this.remove(key);
			return true;
		}
		return false;
	}
	
	private boolean isExpired(Pair<Long, Long> pair, long now) {
		return this.isExpiresAfterAccess() && (now - pair.getLeft() >= this.expireAfterAccessNanos) || (this.isExpiresAfterWrite() && (now - pair.getRight() >= this.expireAfterWriteNanos));
	}
	
	public boolean expires() {
		return this.isExpiresAfterWrite() || this.isExpiresAfterAccess();
	}
	
	private boolean isExpiresAfterAccess() {
		return expireAfterAccessNanos > 0;
	}
	
	private boolean isExpiresAfterWrite() {
		return this.expireAfterWriteNanos > 0;
	}
	
	private void setExpiresAfterAccess(K key, long now) {
		this.expireNanosMap.get(key).setLeft(now);
	}
	
	private void setExpiresAfterWrite(K key, long now) {
		this.expireNanosMap.get(key).setRight(now);
	}
}

