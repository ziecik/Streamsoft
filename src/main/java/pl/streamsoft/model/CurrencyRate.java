package pl.streamsoft.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "currencyrate")
@Table(name = "currencyrate")
public class CurrencyRate {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "currencyname")
    private String currencyName;
    @Enumerated(EnumType.STRING)
    private CurrencyCode code;
    @Column(columnDefinition = "Decimal(10,4)", name = "ratevalue")
    private BigDecimal rateValue;
    @Column(name = "dateofannouncedrate")
    private LocalDate dateOfAnnouncedRate;
    @Column(name = "providername")
    private String providerName = "unknown";
    
    public CurrencyRate() {
    }

    public CurrencyRate(String currencyName, CurrencyCode code, BigDecimal rateValue, LocalDate localDate) {
	this.currencyName = currencyName;
	this.code = code;
	this.rateValue = rateValue;
	this.dateOfAnnouncedRate = localDate;
	this.id = code.toString() + localDate.toString();
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

    @Override
    public String toString() {
	return "CurrencyRate [id=" + id + ", currencyName=" + currencyName + ", code=" + code + ", rateValue="
		+ rateValue + ", dateOfAnnouncedRate=" + dateOfAnnouncedRate + ", providerName=" + providerName + "]";
    }
    
    
}
