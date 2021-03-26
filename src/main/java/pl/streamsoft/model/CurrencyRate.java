package pl.streamsoft.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class CurrencyRate {
    @Id
    private String id;
    private String currencyName;
    @Enumerated(EnumType.STRING)
    private CurrencyCode code;
    @Column(columnDefinition = "Decimal(5,4)")
    private BigDecimal rateValue;
    private LocalDate dateOfAnnouncedRate;
    private String providerName = "unknown";
    
    public CurrencyRate() {
    }

    public CurrencyRate(String currencyName, CurrencyCode code, BigDecimal rateValue, LocalDate localDate) {
	this.currencyName = currencyName;
	this.code = code;
	this.rateValue = rateValue;
	this.dateOfAnnouncedRate = localDate;
	this.id = code.toString() + dateOfAnnouncedRate.toString();
    }

    
    public String getId() {
        return id;
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

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setCode(CurrencyCode code) {
        this.code = code;
    }

    public void setRateValue(BigDecimal rateValue) {
        this.rateValue = rateValue;
    }

    public void setDateOfAnnouncedRate(LocalDate dateOfAnnouncedRate) {
        this.dateOfAnnouncedRate = dateOfAnnouncedRate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderName() {
	return providerName;
    }

    public void setProviderName(String providerName) {
	this.providerName = providerName;
    }
    
    
}
