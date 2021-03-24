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

//    public String getData(CurrencyCode currencyCode, LocalDate localDate) {
//	int attempts = 5;
//
//	while (true) {
//	    try {
//		String providedData = currencyRateProvider.getCurrencyRateData(currencyCode, localDate);
//
////		if (providedData != null) {
////		    return providedData;
////		} else if (--attempts == 0) {
////		    throw new DataNotFoundException(
////			    "Choosen CurrencyRateService does not provide correct data right now. [5 attempts were made]");
////		}
//		return providedData;
//	    } catch (IOException e) {
//		localDate = localDate.minusDays(1);
//		if (--attempts == 0) {
//		    throw new DataNotFoundException(
//			    "Choosen CurrencyRateProvider is not available right now. [5 attempts were made]", e);
//		}
//	    }
//	}
//
//    }

}
