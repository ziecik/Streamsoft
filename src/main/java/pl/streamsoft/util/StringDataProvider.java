package pl.streamsoft.util;


import java.time.LocalDate;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.service.CurrencyRateProvider;

public class StringDataProvider {
    
    public static String getData(CurrencyCode currencyCode, LocalDate localDate,
	    CurrencyRateProvider currencyRateProvider) {
	int attempts = 5;

	while (true) {
	    try {
		String providedData = currencyRateProvider.getCurrencyRateData(currencyCode, localDate);

		return providedData;
	    } catch (DataNotFoundException e) {
		
		if (--attempts == 0) {
		    throw new DataNotFoundException(
			    "Choosen CurrencyRateProvider is not available right now. 5 attempts were made. Last on date: " + localDate.toString(), e);
		}
		localDate = localDate.minusDays(1);
	    }
	}

    }
}
