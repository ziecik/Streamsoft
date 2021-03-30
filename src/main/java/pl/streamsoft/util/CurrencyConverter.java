package pl.streamsoft.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;
import pl.streamsoft.util.cache.CacheMap;
import pl.streamsoft.util.cache.LRUSource;

public class CurrencyConverter {

    private List<CurrencyRateSource> currencyRateSources = new ArrayList<>();

    public CurrencyConverter() {
	this.currencyRateSources.add(LRUSource.lruCashMap);
	this.currencyRateSources.add((CurrencyRateSource) LRUSource.lruCashMap);
	this.currencyRateSources.add(new CurrencyRateRepository());
    }

    public CurrencyConverter(List<CurrencyRateSource> currencyRateSources) {
	this.currencyRateSources = currencyRateSources;
    }

    public ConvertedAmount convertToPLN(AmountDataToConvert amountDataToConvert) {

	DateValidator.validateDate(amountDataToConvert.getDateOfConversion()); // validate date

	CurrencyRate currencyRate = getCurrencyRate(amountDataToConvert.getCurrencyCode(),
		amountDataToConvert.getDateOfConversion());

	ConvertedAmount convertedAmount = new ConvertedAmount(amountDataToConvert, currencyRate);

	return convertedAmount;

    }

    private CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	CurrencyRate currencyRate = null;
	int counter = currencyRateSources.size() -1 ;
	for (CurrencyRateSource currencyRateSource : currencyRateSources) {

	    try {
		currencyRateSource.getCurrencyRate(currencyCode, dateOfConversion);
		
		if (currencyRate != null)
		    break;
		else
		    throw new DataNotFoundException("Data not found in used currency source");
	    } catch (DataNotFoundException e) {
		if (counter-- == 0) {
		    throw new DataNotFoundException(
			    "There is no data in any known source", e);
		}

	    }
	}
	return currencyRate;
    }
}
