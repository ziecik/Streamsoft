package pl.streamsoft.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CSVParser;

public class CurrencyRateCSVFIleService implements CurrencyRateService {

	@Override
	public CurrencyRate getCurrencyRate(CurrencyCode newCurrencyCode, LocalDate localDate) throws IOException {

		List<CurrencyRate> parsedCSVFileToCurrencyRate = CSVParser
				.parseCSVFileToCurrencyRate("/currencyRates.csv");

		
		CurrencyRate currencyRate = (CurrencyRate) parsedCSVFileToCurrencyRate.stream()
				.filter(cr -> cr.getCode().equals(newCurrencyCode)).filter(cr -> cr.getLocalDate().equals(localDate)).collect(Collectors.toList()).get(0);

		return currencyRate;
	}

}
