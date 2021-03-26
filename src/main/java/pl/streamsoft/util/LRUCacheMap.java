package pl.streamsoft.util;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.streamsoft.model.CurrencyRate;

public class LRUCacheMap extends LinkedHashMap<String, CurrencyRate> {

    private static LRUCacheMap cacheMap;
    private int maxSize;

    public static LRUCacheMap getCachemap(int maxSize) {
	if (cacheMap == null)
	    return new LRUCacheMap(maxSize);
	else {
	    return cacheMap;
	}
    }

    private LRUCacheMap() {
	super(20);
	maxSize = 20;
    }

    private LRUCacheMap(int size) {
	super(size);
	this.maxSize = size;
    }

    
    
    @Override
    public CurrencyRate get(Object key) {
	CurrencyRate currencyRate = super.get(key);
	//	moving entry to the end of list
	remove(key);
	put((String) key, currencyRate);
	return currencyRate;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, CurrencyRate> eldest) {
	return size() > maxSize;
    }

}