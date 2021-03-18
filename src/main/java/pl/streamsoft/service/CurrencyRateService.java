package pl.streamsoft.service;

import java.io.IOException;
import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public interface CurrencyRateService {
	public CurrencyRate getCurrencyRate(CurrencyCode newCurrencyCode,
			LocalDate localDate) throws IOException;
}
