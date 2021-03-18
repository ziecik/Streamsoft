package pl.streamsoft.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import pl.streamsoft.exception.CurrencyRateServiceUnavailableException;
import pl.streamsoft.exception.FutureDateException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRatesProvider;

public class CurrencyUtil {

	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate localDate,
			CurrencyRatesProvider currencyRatesProvider) throws FutureDateException {

		CurrencyRate currencyRate;
		BigDecimal result = null;
		if (!DateValidator.isDateValid(localDate)) {
			throw new FutureDateException();
		} else {
			try {
				currencyRate = currencyRatesProvider.getCurrencyRateService().getCurrencyRate(newCurrencyCode,
						localDate);
				BigDecimal rateValue = currencyRate.getRateValue();
				result = rateValue.multiply(valueToConvert).setScale(2, RoundingMode.HALF_DOWN);
				return result;
			} catch (IOException e) {
				throw new CurrencyRateServiceUnavailableException();
			} finally {
				if (result == null)
					throw new CurrencyRateServiceUnavailableException();
			}
		}
	}

}
