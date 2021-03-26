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
	CurrencyCode gbp = CurrencyCode.GBP;
	CurrencyCode eur = CurrencyCode.EUR;
	CurrencyCode huf = CurrencyCode.HUF;
	LocalDate dateOfconversion = LocalDate.parse("2021-03-22");
	
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, gbp, dateOfconversion);
	
	ConvertedAmount convertedAmount = new CurrencyConverter().convertToPLN(amountDataToConvert);
	
//	 amountDataToConvert = new AmountDataToConvert(new BigDecimal("2000"), currencyCode, dateOfconversion);
		
	
	ConvertedAmount convertedAmount2 = new CurrencyConverter().convertToPLN(amountDataToConvert);
	
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.of(2021,03,21)));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-20")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-19")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-18")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-17")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-16")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-15")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-14")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, gbp, LocalDate.parse("2021-03-13")));
//	 
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.of(2021,03,21)));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-20")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-19")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-18")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-17")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-16")));
//
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, huf, LocalDate.parse("2021-03-15")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, huf, LocalDate.parse("2021-03-14")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-17")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.parse("2021-03-16")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, huf, LocalDate.parse("2021-03-13")));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, huf, LocalDate.now()));
//	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, huf, LocalDate.now()));
	 new CurrencyConverter().convertToPLN(new AmountDataToConvert(valueToConvert, eur, LocalDate.now()));


    }
}