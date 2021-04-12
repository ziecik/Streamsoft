package pl.streamsoft.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table
public class CurrencyRate {
    @Id
    @Column(name = "Id")
    private String id;
   
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code")
    private CurrencyInfo currencyInfo;

    @Column(columnDefinition = "Decimal(10,4)", name = "ratevalue")
    private BigDecimal rateValue;
    @Column(name = "dateofannouncedrate")
    private LocalDate dateOfAnnouncedRate;
    @Column(name = "providername")
    private String providerName = "unknown";
    
    public CurrencyRate() {
    }

    public CurrencyRate(CurrencyCode code, BigDecimal rateValue, LocalDate localDate) {
	this.currencyInfo = new CurrencyInfo(code);
	this.rateValue = rateValue;
	this.dateOfAnnouncedRate = localDate;
	this.id = code.toString() + localDate.toString();
    }

     
    
    public CurrencyRate(CurrencyInfo currencyInfo, BigDecimal rateValue, LocalDate dateOfAnnouncedRate) {
	super();
	this.currencyInfo = currencyInfo;
	this.rateValue = rateValue;
	this.dateOfAnnouncedRate = dateOfAnnouncedRate;
	this.id = currencyInfo.getCode().toString() + dateOfAnnouncedRate.toString();
    }

    public String getId() {
        return id;
    }

    public BigDecimal getRateValue() {
	return rateValue;
    }

    public LocalDate getDateOfAnnouncedRate() {
	return dateOfAnnouncedRate;
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
  
    public CurrencyInfo getCurrencyRateInfo() {
        return currencyInfo;
    }

    public void setCurrencyRateInfo(CurrencyInfo currencyRateInfo) {
        this.currencyInfo = currencyRateInfo;
    }

    @Override
    public String toString() {
	return "CurrencyRate [id=" + id + ", currencyName=" + currencyInfo.getCurrencyName() + ", code=" + currencyInfo.getCode() + ", rateValue="
		+ rateValue + ", dateOfAnnouncedRate=" + dateOfAnnouncedRate + ", providerName=" + providerName + "]";
    }
    
    
}
