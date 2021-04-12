package pl.streamsoft.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;
    @NaturalId
    private String countryName;
    @ManyToMany(mappedBy = "countries", cascade = CascadeType.ALL)
    private List<CurrencyInfo> currencies;

    
    
    public Country() {
	super();
    }

    public Country(String countryName, List<CurrencyInfo> currencies) {
	super();
	this.countryName = countryName;
	this.currencies = currencies;
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

}
