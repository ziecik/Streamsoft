package pl.streamsoft.util;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;

public class LRUCacheMap extends LinkedHashMap<String, CurrencyRate> implements Observer {

    private static LRUCacheMap cacheMap;
    private static int maxSize;

    public static LRUCacheMap getCachemap() {
	if (cacheMap == null) {
	    maxSize = 20;
	    return new LRUCacheMap(maxSize);
	} else {
	    return cacheMap;
	}
    }

    private LRUCacheMap() {
	super(20);
	maxSize = 20;
    }

    private LRUCacheMap(int size) {
	super(size);
	maxSize = size;
    }

    @Override
    public CurrencyRate get(Object key) {
	CurrencyRate currencyRate = super.get(key);
	// moving entry to the end of list
	remove(key);
	put((String) key, currencyRate);
	return currencyRate;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, CurrencyRate> eldest) {
	return size() > maxSize;
    }

    @Override
    public void update(Object object) {
	if (!CurrencyConverter.cacheMap.isEmpty()) {
	    CurrencyConverter.cacheMap.put(((CurrencyRate)object).getId(), (CurrencyRate) object);
	}
    }

}