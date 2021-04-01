package pl.streamsoft.util.cache.v1;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CurrencyRateSource;

public class CacheMap extends ConcurrentHashMap<String, CurrencyRate> implements CurrencyRateSource {

    public static CacheMap cacheMap = new CacheMap();

//    public static CacheMap getInstance() {
//	return cacheMap;
//    }

    public CacheMap() {
	super(20);
    }

    public CacheMap(int size) {
	super(size);
    }

    @Override
    public CurrencyRate get(Object key) {
	CurrencyRate currencyRate = super.get(key);
	return currencyRate;
    }

    @Override
    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	CurrencyRate currencyRate = get(currencyCode.toString() + dateOfConversion.toString());

	return Optional.ofNullable(currencyRate).orElseThrow(() -> new DataNotFoundException("Data not found in cache"));
    }

}