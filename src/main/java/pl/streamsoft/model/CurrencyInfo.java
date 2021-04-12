package pl.streamsoft.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class CurrencyInfo {

    @Id
    @Enumerated(EnumType.STRING)
    private CurrencyCode code;

    @Column(name = "currencyname")
    private String currencyName;

    @OneToMany(mappedBy = "currencyInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CurrencyRate> currencyRates;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "relationTable", joinColumns = { @JoinColumn(name = "currency_code") }, inverseJoinColumns = {
	    @JoinColumn(name = "countryId") })
    private List<Country> countries;

    public CurrencyInfo() {
    }
   
    public CurrencyInfo(CurrencyCode code) {
	super();
	this.code = code;
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

    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(List<CurrencyRate> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    
    

}