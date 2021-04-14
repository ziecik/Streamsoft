package pl.streamsoft.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;
    @NaturalId
    private String countryName;
    @ManyToMany(mappedBy = "countries", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CurrencyInfo> currencies;
    
    private String capitalCity;
    
    public Country() {
	super();
    }
    
    public Country(String countryName) {
	super();
	this.countryName = countryName;
    }
    
    public Country(String countryName, String capitalCity) {
	super();
	this.countryName = countryName;
	this.capitalCity = capitalCity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
	return countryName;
    }

    public void setCountryName(String countryName) {
	this.countryName = countryName;
    }

    public List<CurrencyInfo> getCurrencies() {
	return currencies;
    }

    public void setCurrencies(List<CurrencyInfo> currencies) {
	this.currencies = currencies;
    }

    public void addCurrency(CurrencyInfo currencyInfo) {
	this.currencies.add(currencyInfo);
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    
}
