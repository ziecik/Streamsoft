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

public class CurrencyConverter {

    private List<CurrencyRateSource> currencyRateSources = new ArrayList<>();

    public CurrencyConverter() {
	this.currencyRateSources.add(CacheMap.cacheMap);
	this.currencyRateSources.add(new CurrencyRateSource() {
	    
	    @Override
	    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
		// TODO Auto-generated method stub
		return null;
	    }
	});
//	this.currencyRateSources.add(new ExternalCurrencyRateSource());
//	this.currencyRateSources.add(new CurrencyRateRepository());
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
	for (CurrencyRateSource currencyRateSource : currencyRateSources) {

	    try {
		currencyRate = currencyRateSource.getCurrencyRate(currencyCode, dateOfConversion);
		if (currencyRate != null)
		    break;
	    } catch (DataNotFoundException e) {
		if (currencyRateSource == currencyRateSources.get(currencyRateSources.size() - 1)) {
		    throw new DataNotFoundException("There is no data in any known source: " + this.currencyRateSources.toString(),
			    e);
		}

	    }
	}
	return currencyRate;
    }
}
