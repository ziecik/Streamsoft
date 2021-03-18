package pl.streamsoft.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import pl.streamsoft.exception.CurrencyRateServiceUnavailableException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRateService;

public class CurrencyUtil {

	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate localDate,
			CurrencyRateService currencyRateService) {

		CurrencyRate currencyRate;
		BigDecimal result = null;
		try {
			currencyRate = currencyRateService.getCurrencyRate(newCurrencyCode, localDate);
			BigDecimal rateValue = currencyRate.getRateValue();
			result = rateValue.multiply(valueToConvert).setScale(2, RoundingMode.HALF_DOWN);
			return result;
		} catch (IOException e) {
			throw new CurrencyRateServiceUnavailableException();
		} finally {
			if(result == null)
				throw new CurrencyRateServiceUnavailableException();
		}
	}
}
