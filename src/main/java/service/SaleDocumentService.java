package service;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.CurrencyCode;
import util.CurrencyUtil;

public class SaleDocumentService {
	
	public void insert() {
		
		CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.GBP, LocalDate.parse("2021-03-13"));
		CurrencyUtil.convertToPLN(new BigDecimal("127"), CurrencyCode.GBP, LocalDate.now());

//		 CurrencyUtil.getMinValue(CurrencyCode.USD, LocalDate.parse("2012-10-11"), LocalDate.parse("2013-05-05"));

		
	}
}