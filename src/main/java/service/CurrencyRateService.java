package service;

import java.time.LocalDate;

import model.CurrencyCode;
import model.CurrencyRate;

public interface CurrencyRateService {
	public CurrencyRate getCurrencyRate(CurrencyCode newCurrencyCode,
			LocalDate localDate);
}
