package pl.streamsoft.util.cache.v3;

import java.util.Optional;

public interface GenericCahce<K, V> {
    void put(K key, V value);
    Optional<V> get(K key); //	optional?
    void remove(K key);
    boolean containsKey(K key);
    void cleanExpierdEntries();
    void clearAll();
}
