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

    public String getCurrencyName() {
	return currencyName;
    }

    public CurrencyCode getCode() {
	return code;
    }

    public BigDecimal getRateValue() {
	return rateValue;
    }

    public LocalDate getDateOfAnnouncedRate() {
	return dateOfAnnouncedRate;
    }

}
