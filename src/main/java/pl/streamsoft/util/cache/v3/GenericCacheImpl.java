package pl.streamsoft.util.cache.v3;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GenericCacheImpl<K, V> implements GenericCahce<K, V> {

    public static final long DEFAULT_CACHE_TIMEOUT = 60 * 1000L;
    private long cacheTimeout;

    private Map<K, CacheValue<V>> cacheMap;

    public GenericCacheImpl() {
	this.cacheTimeout = DEFAULT_CACHE_TIMEOUT;
	clearAll();
    }

    public GenericCacheImpl(long cacheTimeout) {
	this.cacheTimeout = cacheTimeout;
	clearAll();
    }

    @Override
    public synchronized void put(K key, V value) {
	cacheMap.put(key, createCacheValue(value));
    }

    @Override
    public synchronized Optional<V> get(K key) {
	cleanExpierdEntries();
	return Optional.ofNullable(cacheMap.get(key)).map(CacheValue::getValue);
    }

    @Override
    public void remove(K key) {
	cacheMap.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
	return cacheMap.containsKey(key);
    }

    @Override
    public void cleanExpierdEntries() {
	getExpiredKeys().stream().forEach(this::remove);
    }

    @Override
    public void clearAll() {
	cacheMap = new ConcurrentHashMap<>();
    }

    private boolean isExpired(K key) {
	LocalDateTime expirationTime = cacheMap.get(key).getCreatedAt().plus(cacheTimeout, ChronoUnit.MILLIS);
	return LocalDateTime.now().isAfter(expirationTime);
    }
    
    private Set<K> getExpiredKeys() {
	return cacheMap.keySet().stream().filter(this::isExpired).collect(Collectors.toSet());
    }
    
    private CacheValue<V> createCacheValue(V value) {
	
	LocalDateTime now = LocalDateTime.now();
	
	return new CacheValue<V>() {

	    @Override
	    public V getValue() {
		return value;
	    }

	    @Override
	    public LocalDateTime getCreatedAt() {
		return now;
	    }
	};

    }

    @Override
    public boolean isEmpty() {
	return cacheMap.isEmpty();
    }

}
