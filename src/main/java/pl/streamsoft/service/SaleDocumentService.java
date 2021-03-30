package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;
import pl.streamsoft.util.CurrencyConverter;

public class SaleDocumentService {

    public void insert() {
	BigDecimal valueToConvert = new BigDecimal("1499.99");
	CurrencyCode eur = CurrencyCode.EUR;

	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, eur,
		LocalDate.parse("2021-03-01"));

	ConvertedAmount amount1 = new CurrencyConverter().convertToPLN(amountDataToConvert);

//	new CurrencyRateRepository().update(eur.toString() + "2021-03-17",
//		new CurrencyRate("euro", eur, new BigDecimal("8.8888"), LocalDate.parse("2021-03-17")));
//
//	ConvertedAmount amount2 = new CurrencyConverter().convertToPLN(amountDataToConvert);

    }
}