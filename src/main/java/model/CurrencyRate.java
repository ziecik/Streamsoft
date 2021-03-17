package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyRate {

	private String currencyName;
	private String code;
	private BigDecimal rate;
	private LocalDate date;
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return code + 
				":\t" + rate + "\t" +  date + "\t" + 
				"\n"; 
	}
}
