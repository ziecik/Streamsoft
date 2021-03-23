package pl.streamsoft.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ConvertedAmount {
//    private AmountDataToConvert amountDataToConvert;
//    private CurrencyRate currencyRateUsedToConvertion;
    private BigDecimal convertedValue;

    private BigDecimal valueToConvert;
    private CurrencyCode currencyCode;
    private LocalDate dateOfConversion;
    private String currencyName;
    private LocalDate dateOfAnnoncedData;
    private BigDecimal rateValue;

    public ConvertedAmount(AmountDataToConvert amountDataToConvert, CurrencyRate currencyRateUsedToConvertion) {
//	this.amountDataToConvert = amountDataToConvert;
//	this.currencyRateUsedToConvertion = currencyRateUsedToConvertion;
	//
	this.valueToConvert = amountDataToConvert.getValueToConvert();
	this.currencyCode = amountDataToConvert.getCurrencyCode();
	this.dateOfConversion = amountDataToConvert.getDateOfConversion();
	this.currencyName = currencyRateUsedToConvertion.getCurrencyName();
	this.dateOfAnnoncedData = currencyRateUsedToConvertion.getDateOfAnnouncedRate();
	this.rateValue = currencyRateUsedToConvertion.getRateValue();
	//
	this.convertedValue = calculateConvertedValue();
    }

    public BigDecimal getValueToConvert() {
	return valueToConvert;
    }

    public CurrencyCode getCurrencyCode() {
	return currencyCode;
    }

    public LocalDate getDateOfConversion() {
	return dateOfConversion;
    }

    public String getCurrencyName() {
	return currencyName;
    }

    public LocalDate getDateOfAnnoncedData() {
	return dateOfAnnoncedData;
    }

    public BigDecimal getRateValue() {
	return rateValue;
    }

    public BigDecimal getConvertedValue() {
	return convertedValue;
    }

    private BigDecimal calculateConvertedValue() {
	return valueToConvert.multiply(rateValue).setScale(2, RoundingMode.HALF_DOWN);
    }

}
