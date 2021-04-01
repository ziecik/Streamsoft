package pl.streamsoft.util.cache.v2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRU<K, V> {
    private int size;
    private ConcurrentLinkedQueue<K> queue;
    private ConcurrentHashMap<K, V> map;

    public LRU(int size) {
	this.size = size;
	this.queue = new ConcurrentLinkedQueue<>();
	this.map = new ConcurrentHashMap<>(size);
    }

    public V get(K key) {
	V value = map.get(key);
	if (value != null) {
	    queue.remove(key);
	    queue.add(key);
	}
	return value;
    }

    
    // put - update
    public synchronized void put(K key, V value) {
	if (map.contains(key))
	    queue.remove(key);

	while (queue.size() >= size) {
	    K oldestKey = queue.poll();

	    if (oldestKey != null) {
		map.remove(oldestKey);
	    }
	}
	    queue.add(key);
	    map.put(key, value);
	
    }

    public int getSize() {
        return size;
    }

    public ConcurrentLinkedQueue<K> getQueue() {
        return queue;
    }

    public ConcurrentHashMap<K, V> getMap() {
        return map;
    }
    
    
}
