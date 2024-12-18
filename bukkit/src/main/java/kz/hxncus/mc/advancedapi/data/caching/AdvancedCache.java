package kz.hxncus.mc.advancedapi.data.caching;

import kz.hxncus.mc.advancedapi.api.data.caching.LocalCache;

import java.util.concurrent.TimeUnit;

public class AdvancedCache<K, V> extends LocalCache<K, V> {
	public AdvancedCache() {
		super();
	}
	
	public AdvancedCache(final long expireAfterAccessNanos, final long expireAfterWriteNanos) {
		super(expireAfterAccessNanos, expireAfterWriteNanos);
	}
	
	public AdvancedCache(final long expireAfterAccess, final long expireAfterWrite, TimeUnit timeUnit) {
		super(expireAfterAccess, expireAfterWrite, timeUnit);
	}
}
