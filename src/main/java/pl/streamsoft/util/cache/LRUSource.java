package pl.streamsoft.util.cache;

import java.time.LocalDate;
import java.util.Optional;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public class LRUSource extends LRU<String, CurrencyRate> implements Cacheable {

    public static LRUSource lruCashMap = new LRUSource(5);
    
    public LRUSource(int size) {
	super(size);
    }

    @Override
    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	CurrencyRate currencyRate = lruCashMap.get(currencyCode.toString() + dateOfConversion.toString());
	return Optional.ofNullable(currencyRate).orElseThrow(() -> new DataNotFoundException("Data not found in LRUSource"));
    }

    @Override
    public void update(CurrencyRate currencyRate) {
	super.put(currencyRate.getId(), currencyRate);
    }

}
