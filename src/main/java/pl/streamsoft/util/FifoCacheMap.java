package pl.streamsoft.util;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.streamsoft.model.CurrencyRate;

public class FifoCacheMap extends LinkedHashMap<String, CurrencyRate> {

    private static FifoCacheMap cacheMap;
    private int maxSize;

    public static FifoCacheMap getCachemap(int maxSize) {
	if (cacheMap == null)
	    return new FifoCacheMap(maxSize);
	else {
	    return cacheMap;
	}
    }

    private FifoCacheMap() {
	super(20);
	maxSize = 20;
    }

    private FifoCacheMap(int size) {
	super(size);
	this.maxSize = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, CurrencyRate> eldest) {
	return size() > maxSize;
    }

}