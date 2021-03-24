package pl.streamsoft.util;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRateProvider;
import pl.streamsoft.service.CurrencyRateProviderNBP;
import pl.streamsoft.service.StringToObjectConverter;

public class CurrencyConverter {
    private CurrencyRateProvider currencyRateProvider;
    private StringToObjectConverter dataToObjectConverter;

    public CurrencyConverter() {
	this.currencyRateProvider = new CurrencyRateProviderNBP();
	this.dataToObjectConverter = new JSONConverterNBP();
    }

    public CurrencyConverter(CurrencyRateProvider currencyRateProvider, StringToObjectConverter dataToObjectConverter) {
	this.currencyRateProvider = currencyRateProvider;
	this.dataToObjectConverter = dataToObjectConverter;
    }



    public ConvertedAmount convertToPLN(AmountDataToConvert amountDataToConvert) {

	DateValidator.validateDate(amountDataToConvert.getDateOfConversion()); // validate date

	String providedData = StringDataProvider.getData(amountDataToConvert.getCurrencyCode(), amountDataToConvert.getDateOfConversion(), currencyRateProvider);

	CurrencyRate currencyRate = dataToObjectConverter.convertToCurrencyRate(providedData);

	ConvertedAmount convertedAmount = new ConvertedAmount(amountDataToConvert, currencyRate);

	return convertedAmount;

    }

}
