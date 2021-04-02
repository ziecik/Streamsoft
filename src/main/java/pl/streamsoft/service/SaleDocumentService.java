package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;
import pl.streamsoft.util.CurrencyConverter;
import pl.streamsoft.util.cache.v2.LRUSource;

public class SaleDocumentService {

    public void insert() {

	BigDecimal valueToConvert = new BigDecimal("1499.99");
	CurrencyCode code = CurrencyCode.USD;
	LocalDate date = LocalDate.of(2009, 3, 31);

	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, code, date);

	CurrencyConverter currencyConverter = new CurrencyConverter();
	ConvertedAmount amount1 = currencyConverter.convertToPLN(amountDataToConvert);


    }
}