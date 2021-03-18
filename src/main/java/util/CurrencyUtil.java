package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import model.CurrencyCode;
import model.CurrencyRate;
import service.CurrencyRateService;

public class CurrencyUtil {

	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate localDate,
			CurrencyRateService currencyRateService) {

		CurrencyRate currencyRate = currencyRateService.getCurrencyRate(newCurrencyCode, localDate);
		BigDecimal rateValue = currencyRate.getRateValue();
		BigDecimal result = rateValue.multiply(valueToConvert).setScale(2, RoundingMode.HALF_DOWN);

		return result;
	}
}
