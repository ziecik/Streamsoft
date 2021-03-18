package service;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.CurrencyCode;
import util.CurrencyUtil;

public class SaleDocumentService {
	
	public void insert() {
		CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.KRW, LocalDate.now(), new CurrencyRateNBPService());
	}
}