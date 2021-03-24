package pl.streamsoft.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AmountDataToConvert {
    
    private BigDecimal valueToConvert;
    private CurrencyCode currencyCode;
    private LocalDate dateOfConversion;
    
    public AmountDataToConvert(BigDecimal valueToConvert, CurrencyCode currencyCode, LocalDate dateOfConversion) {
	this.valueToConvert = valueToConvert;
	this.currencyCode = currencyCode;
	this.dateOfConversion = dateOfConversion;
    }


    public void setValueToConvert(BigDecimal valueToConvert) {
	this.valueToConvert = valueToConvert;
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

}
