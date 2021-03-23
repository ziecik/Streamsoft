package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.util.CurrencyConverter;

public class SaleDocumentService {

    public void insert() {
	ConvertedAmount valeInPLN = new CurrencyConverter().convertToPLN(new AmountDataToConvert(new BigDecimal("1499.99"), CurrencyCode.GBP, LocalDate.parse("2021-03-21")));
    }
}