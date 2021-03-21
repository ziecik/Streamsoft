package pl.streamsoft.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyRate extends SimpleCurrencyRate {
	private String currencyName;
	private LocalDate localDate;

	public CurrencyRate() {
	}

	public CurrencyRate(CurrencyCode code, BigDecimal rateValue) {
		super(code, rateValue);
	}

	public CurrencyRate(String currencyName, CurrencyCode code, BigDecimal rateValue, LocalDate localDate) {
		super(code, rateValue);
		this.currencyName = currencyName;
		this.localDate = localDate;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

}
