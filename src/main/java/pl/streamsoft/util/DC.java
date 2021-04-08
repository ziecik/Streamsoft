package pl.streamsoft.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.CurrencyCode;

public class DC {

    public static void convertFromAllCurrencies() {
	CurrencyConverter currencyConverter = new CurrencyConverter();
	for (int i = 0; i <21; i++) {
//	    for (int j = 0; j < CurrencyCode.values().length; j++) {
		currencyConverter.convertToPLN(new AmountDataToConvert(BigDecimal.TEN, CurrencyCode.EUR, LocalDate.of(2021,4,8).minusDays(i)));
//	    }
	}
    }
}
