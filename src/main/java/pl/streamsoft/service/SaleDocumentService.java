package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.util.CurrencyConverter;

public class SaleDocumentService {

	public void insert() {
		BigDecimal valeInPLN = new CurrencyConverter().convertToPLN(new BigDecimal("1000"), CurrencyCode.EUR,
				LocalDate.parse("2020-12-04"));
	}
}