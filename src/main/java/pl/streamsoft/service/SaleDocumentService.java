package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.util.CurrencyUtil;

public class SaleDocumentService {
	
	public void insert() {
		BigDecimal valeInPLN = CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.EUR, LocalDate.parse("2020-03-19"), CurrencyRatesProvider.NBP);
	
	}
}