package service;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.CurrencyCode;
import util.CurrencyUtil;

public class SaleDocumentService {
	
	public void insert() {
		BigDecimal valeInPLN = CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.EUR, LocalDate.parse("2921-03-15"), new CurrencyRateNBPService());
	}
}