package pl.streamsoft.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyRate {
    private String currencyName;
    private CurrencyCode code;
    private BigDecimal rateValue;
    private LocalDate dateOfAnnouncedRate;

    public CurrencyRate() {
    }

    public CurrencyRate(String currencyName, CurrencyCode code, BigDecimal rateValue, LocalDate localDate) {
	this.currencyName = currencyName;
	this.code = code;
	this.rateValue = rateValue;
	this.dateOfAnnouncedRate = localDate;
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

    public String getCurrencyName() {
	return currencyName;
    }

    public void setCurrencyName(String currencyName) {
	this.currencyName = currencyName;
    }

    public LocalDate getDateOfAnnouncedRate() {
	return dateOfAnnouncedRate;
    }

    public void setDateOfAnnouncedRate(LocalDate localDate) {
	this.dateOfAnnouncedRate = localDate;
    }

}
