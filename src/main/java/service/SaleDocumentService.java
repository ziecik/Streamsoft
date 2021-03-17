package service;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.CurrencyCode;
import util.CurrencyUtil;

public class SaleDocumentService {
	
	public void insert() {
		
		BigDecimal convertToPLN = CurrencyUtil.convertToPLN(new BigDecimal("17867.35"), CurrencyCode.GBP, LocalDate.parse("2021-03-13"));
		BigDecimal convertToPLN2 = CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.GBP, LocalDate.now());

	}
}