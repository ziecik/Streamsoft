package pl.streamsoft.util.cache;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CurrencyRateSource;

public class LRUCacheMap extends LinkedHashMap<String, CurrencyRate> implements CurrencyRateSource {

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
    public synchronized CurrencyRate get(Object key) {
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

    @Override
    public synchronized CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	CurrencyRate currencyRate = get(currencyCode.toString() + dateOfConversion.toString());
	return Optional.ofNullable(currencyRate).orElseThrow(() -> new DataNotFoundException("Data not found in cache"));
    }

}
