package pl.streamsoft.util;

import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRateProvider;
import pl.streamsoft.service.CurrencyRateProviderNBP;
import pl.streamsoft.service.JSONParserNBP;
import pl.streamsoft.service.StringToObjectParser;

public class ExternalCurrencyRateSource implements CurrencyRateSource {
    private CurrencyRateProvider currencyRateProvider;
    private StringToObjectParser dataToObjectConverter;

    
    
    public ExternalCurrencyRateSource() {
	this.currencyRateProvider = new CurrencyRateProviderNBP();
	this.dataToObjectConverter = new JSONParserNBP();
    }

    public ExternalCurrencyRateSource(CurrencyRateProvider currencyRateProvider,
	    StringToObjectParser dataToObjectConverter) {
	this.currencyRateProvider = currencyRateProvider;
	this.dataToObjectConverter = dataToObjectConverter;
    }

    @Override
    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	
	String providedData = StringDataProvider.getData(currencyCode, dateOfConversion, currencyRateProvider);
	CurrencyRate currencyRate = dataToObjectConverter.convertToCurrencyRate(providedData);
	
	return currencyRate;
    }

}
