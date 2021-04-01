package pl.streamsoft.util.cache.v3;

import java.time.LocalDate;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.cache.Cacheable;

public class CurrencyRateCache extends GenericCacheImpl<String, CurrencyRate> implements Cacheable {

    @Override
    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	return (get(currencyCode.toString() + dateOfConversion.toString())).orElseThrow(() -> new DataNotFoundException(""));
    }

    @Override
    public void update(CurrencyRate currencyRate) {
	super.put(currencyRate.getId(), currencyRate);
    }

}
