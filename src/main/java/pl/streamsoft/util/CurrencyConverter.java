package pl.streamsoft.util;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRateProvider;
import pl.streamsoft.service.CurrencyRateProviderNBP;
import pl.streamsoft.service.CurrencyRateRepository;
import pl.streamsoft.service.JSONParserNBP;
import pl.streamsoft.service.StringToObjectParser;

public class CurrencyConverter {
    private CurrencyRateProvider currencyRateProvider;
    private StringToObjectParser dataToObjectConverter;
    private CurrencyRateRepository currencyRateRepository = new CurrencyRateRepository();
    public static LRUCacheMap cacheMap = LRUCacheMap.getCachemap(5);

    public CurrencyConverter() {
	this.currencyRateProvider = new CurrencyRateProviderNBP();
	this.dataToObjectConverter = new JSONParserNBP();
    }

    public CurrencyConverter(CurrencyRateProvider currencyRateProvider, StringToObjectParser dataToObjectConverter) {
	this.currencyRateProvider = currencyRateProvider;
	this.dataToObjectConverter = dataToObjectConverter;
    }

    public ConvertedAmount convertToPLN(AmountDataToConvert amountDataToConvert) {

	DateValidator.validateDate(amountDataToConvert.getDateOfConversion()); // validate date

	CurrencyRate currencyRate;

	String key = amountDataToConvert.getCurrencyCode().toString() + amountDataToConvert.getDateOfConversion();

	if (cacheMap.containsKey(key)) {

	    currencyRate = cacheMap.get(key);

	} else if ((currencyRate = currencyRateRepository.find(key)) != null) {

	    cacheMap.put(key, currencyRate);

	} else { // getData from some exteranal source
	    String providedData = StringDataProvider.getData(amountDataToConvert.getCurrencyCode(),
		    amountDataToConvert.getDateOfConversion(), currencyRateProvider);

	    currencyRate = dataToObjectConverter.convertToCurrencyRate(providedData);
	    currencyRate.setId(key);
	    currencyRate.setProviderName(currencyRateProvider.getClass().getSimpleName());
	    cacheMap.put(key, currencyRate);

	    currencyRateRepository.add(currencyRate);

	    System.out.println(cacheMap);
	}
	
	ConvertedAmount convertedAmount = new ConvertedAmount(amountDataToConvert, currencyRate);

	return convertedAmount;

    }

}