package pl.streamsoft.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CurrencyInfo {

    @Id
    @Enumerated(EnumType.STRING)
    private CurrencyCode code;

    @Column(name = "currencyname")
    private String currencyName;

    @OneToMany(mappedBy = "currencyInfo", cascade = CascadeType.ALL)
    private List<CurrencyRate> currencyRates;

    public CurrencyInfo() {
    }

    public CurrencyInfo(CurrencyCode code, String currencyName) {
	this.code = code;
	this.currencyName = currencyName;
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

}