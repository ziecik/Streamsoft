package pl.streamsoft.util;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public class CacheMap extends ConcurrentHashMap<String, CurrencyRate> implements CurrencyRateSource {

    public static CacheMap cacheMap = new CacheMap();

    public static CacheMap getInstance() {
	return cacheMap;
    }

    private int maxSize;

    public CacheMap() {
	super(20);
	maxSize = 20;
    }

    public CacheMap(int size) {
	super(size);
	maxSize = size;
    }

    @Override
    public CurrencyRate get(Object key) {
	CurrencyRate currencyRate = super.get(key);
	return currencyRate;
    }

    @Override
    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	CurrencyRate currencyRate = get(currencyCode.toString() + dateOfConversion.toString());
	if (currencyRate != null)
	    return currencyRate;
	else
	    throw new DataNotFoundException("Data not found in cache");
    }

}