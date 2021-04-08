package pl.streamsoft.dto;

import java.math.BigDecimal;

import pl.streamsoft.model.CurrencyCode;

public class RateDifference {
    private CurrencyCode code;
    private String currencyName;
    private BigDecimal difference;
    private Period period;

    public RateDifference(CurrencyCode code, String currencyName, BigDecimal difference, Period period) {
	super();
	this.code = code;
	this.currencyName = currencyName;
	this.difference = difference;
	this.period = period;
    }

    public CurrencyCode getCode() {
	return code;
    }

    public void setCode(CurrencyCode code) {
	this.code = code;
    }

    public String getCurrencyName() {
	return currencyName;
    }

    public void setCurrencyName(String currencyName) {
	this.currencyName = currencyName;
    }

    public Period getPeriod() {
	return period;
    }

    public void setPeriod(Period period) {
	this.period = period;
    }

    public BigDecimal getDifference() {
	return difference;
    }

    public void setDifference(BigDecimal difference) {
	this.difference = difference;
    }

    public Period getData() {
	return period;
    }

    public void setData(Period data) {
	this.period = data;
    }

}
