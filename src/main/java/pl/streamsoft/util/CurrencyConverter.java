package pl.streamsoft.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.SimpleCurrencyRate;
import pl.streamsoft.service.CurrencyRateNBPService;
import pl.streamsoft.service.CurrencyRateService;

public class CurrencyConverter {
	private CurrencyRateService currencyRateService;

	
	public CurrencyConverter() {
		this.currencyRateService = new CurrencyRateNBPService();
	}

	public CurrencyConverter(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	
	public BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode currencyCode, LocalDate localDate) {

		DateValidator.validateDate(localDate);
		SimpleCurrencyRate simpleRate = this.getRate(currencyRateService, currencyCode, localDate);
		BigDecimal convertedToPLN = this.makeConvertion(valueToConvert, simpleRate.getRateValue());

		return convertedToPLN;

	}

	private SimpleCurrencyRate getRate(CurrencyRateService currencyRateService, CurrencyCode currencyCode,
			LocalDate localDate) {
		int attempts = 5;

		while (true) {
			try {
				SimpleCurrencyRate simpleCurrencyRate = currencyRateService.getCurrencyRate(currencyCode, localDate);
				if (simpleCurrencyRate instanceof SimpleCurrencyRate) {	//	null value from currencyRateService.getCurrencyRate
					return simpleCurrencyRate;
				} else if (--attempts == 0) {
					throw new DataNotFoundException("Choosen CurrencyRateService does not provide correct data right now. [5 attempts were made]", new IOException());
				}

			} catch (IOException e) {
				localDate = localDate.minusDays(1);
				if (--attempts == 0)
					throw new DataNotFoundException("Choosen CurrencyRateService is not available right now. [5 attempts were made]", e);
			}
		}

	}

	private BigDecimal makeConvertion(BigDecimal valueToConvert, BigDecimal rateValue) {
		return valueToConvert.multiply(rateValue).setScale(2, RoundingMode.HALF_DOWN);
	}
}
