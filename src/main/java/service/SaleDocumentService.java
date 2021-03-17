package service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import model.CurrencyCode;
import model.CurrencyRate;
import util.CurrencyUtil;

public class SaleDocumentService {
	
	public void insert() {

		List<CurrencyRate> currentCurrencyRates = CurrencyUtil.getCurrentCurrencyRates();

		CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.GBP, LocalDate.parse("2021-03-13"));
		CurrencyUtil.convertToPLN(new BigDecimal("127"), CurrencyCode.GBP, LocalDate.now());

		CurrencyUtil.getCurrencyRate(CurrencyCode.GBP, LocalDate.parse("2021-03-14"));

		
	}
}