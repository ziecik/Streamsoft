package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyRate {

	private String currencyName;
	private CurrencyCode code;
	private BigDecimal rateValue;
	private LocalDate localDate;
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public CurrencyCode getCode() {
		return code;
	}
	public void setCode(CurrencyCode code) {
		this.code = code;
	}
	public BigDecimal getRateValue() {
		return rateValue;
	}
	public void setRateValue(BigDecimal rateValue) {
		this.rateValue = rateValue;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	
	@Override
	public String toString() {
		return code + 
				":\t" + rateValue + "\t" + localDate + "\t" +  currencyName +
				"\n"; 
	}
}
