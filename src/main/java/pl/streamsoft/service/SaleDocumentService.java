package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.util.CurrencyConverter;

public class SaleDocumentService {

    public void insert() {
	BigDecimal valueToConvert = new BigDecimal("1499.99");
	CurrencyCode currencyCode = CurrencyCode.GBP;
	LocalDate dateOfconversion = LocalDate.parse("2021-03-22");
	
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, currencyCode, dateOfconversion);
	
	ConvertedAmount convertedAmount = new CurrencyConverter().convertToPLN(amountDataToConvert);
    }
}