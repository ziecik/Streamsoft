package pl.streamsoft.model;

import java.math.BigDecimal;

public class SimpleCurrencyRate {
	private CurrencyCode code;
	private BigDecimal rateValue;

	public SimpleCurrencyRate() {}

	public SimpleCurrencyRate(CurrencyCode code, BigDecimal rateValue) {
		this.code = code;
		this.rateValue = rateValue;
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

}
