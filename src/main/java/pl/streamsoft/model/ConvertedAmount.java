package pl.streamsoft.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConvertedAmount {
    private AmountDataToConvert amountDataToConvert;
    private CurrencyRate currencyRateUsedToConvertion;
    private BigDecimal convertedValue;


    public ConvertedAmount(AmountDataToConvert amountDataToConvert, CurrencyRate currencyRateUsedToConvertion) {
	this.amountDataToConvert = amountDataToConvert;
	this.currencyRateUsedToConvertion = currencyRateUsedToConvertion;
	
	this.convertedValue = calculateConvertedValue();
    }

    public AmountDataToConvert getAmountDataToConvert() {
        return amountDataToConvert;
    }


    public void setAmountDataToConvert(AmountDataToConvert amountDataToConvert) {
        this.amountDataToConvert = amountDataToConvert;
    }


    public CurrencyRate getCurrencyRateUsedToConvertion() {
        return currencyRateUsedToConvertion;
    }


    public void setCurrencyRateUsedToConvertion(CurrencyRate currencyRateUsedToConvertion) {
        this.currencyRateUsedToConvertion = currencyRateUsedToConvertion;
    }


    public BigDecimal getConvertedValue() {
        return convertedValue;
    }


    private BigDecimal calculateConvertedValue() {
	BigDecimal valueToConvert = amountDataToConvert.getValueToConvert();
	BigDecimal rateValue = currencyRateUsedToConvertion.getRateValue();
	return valueToConvert.multiply(rateValue).setScale(2, RoundingMode.HALF_DOWN);
    }

    public ConvertedAmount recalculateConvertedValue(BigDecimal valueToRecalculate) {
	amountDataToConvert.setValueToConvert(valueToRecalculate);
	return new ConvertedAmount(amountDataToConvert, currencyRateUsedToConvertion);
    }
    
}
