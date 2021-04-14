package test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import pl.streamsoft.dto.RateDifference;
import pl.streamsoft.model.Country;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CountryRepository;
import pl.streamsoft.repository.CurrencyInfoRepository;
import pl.streamsoft.repository.CurrencyRateRepository;

public class DBQueriesTest {

//    EntityManagerFactory entityManagerFactory;
//    EntityManager entityManager;
    String persistenceUnitName = "dbH2Test";

    CurrencyRateRepository currencyRateRepository = new CurrencyRateRepository(persistenceUnitName);
    CurrencyInfoRepository currencyInfoRepository = new CurrencyInfoRepository(persistenceUnitName);
    CountryRepository countryRepository = new CountryRepository(persistenceUnitName);
    BigDecimal minRate = new BigDecimal("4.1175");

    LocalDate start = LocalDate.now().minusDays(6);
    LocalDate end = LocalDate.now();
    int limit = 1;

    //	#1
    @Test
    void should_returnEUR_when_selectCurrencyWithMaxDifferenceInPeriod() {

//	given:
	setGiven1();

//	when:
	List<RateDifference> differences = currencyRateRepository.findCurrencyRateWithMaxDifferenceValueBetween(start,
		end, limit);
	RateDifference rateDifference = differences.get(0);

//	then:
	Assertions.assertThat(rateDifference.getCode()).isEqualTo(CurrencyCode.EUR);
	Assertions.assertThat(rateDifference.getDifference()).isEqualTo(new BigDecimal("0.1").setScale(4));
    }

    public void setGiven1() {
	CurrencyRate eur1 = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.1234"), LocalDate.now());

	LocalDate yesterday = LocalDate.now().minusDays(1);
	CurrencyRate eur2 = new CurrencyRate(CurrencyCode.EUR, "euro", minRate, yesterday);
	CurrencyRate eur3 = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.2175"),
		LocalDate.now().minusDays(2));
	CurrencyRate eur4 = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.2097"),
		LocalDate.now().minusDays(3));
	CurrencyRate eur5 = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.1985"),
		LocalDate.now().minusDays(4));
	CurrencyRate eur6 = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.2109"),
		LocalDate.now().minusDays(5));
	CurrencyRate eur7 = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.2111"),
		LocalDate.now().minusDays(6));

	currencyRateRepository.add(eur1);
	currencyRateRepository.add(eur2);
	currencyRateRepository.add(eur3);
	currencyRateRepository.add(eur4);
	currencyRateRepository.add(eur5);
	currencyRateRepository.add(eur6);
	currencyRateRepository.add(eur7);
    }

    // #2
    @Test
    void should_returnEuroRateFromYesterday_when_selectMinRateInPeriod() {
//	given:
	setGiven1();
	
//	when:
	List<CurrencyRate> minCurrencyRate = currencyRateRepository.findCurrencyRateWithMinValueBetween(start, end, CurrencyCode.EUR);
	
//	then:
	Assertions.assertThat(minCurrencyRate.get(0).getRateValue()).isEqualTo(minRate);
    }

    //	#3
    @Test
    void should_notReturnMinRate_when_select5bestRatesInPeriod() {
//	given:
	setGiven1();
	
//	when:
	List<CurrencyRate> best5Rates = currencyRateRepository.find5BestRatesForCurrency(CurrencyCode.EUR);
	
//	then:
	Assertions.assertThat(best5Rates.get(4).getRateValue()).isGreaterThan(minRate);
    }
    
    //	#4
    @Test
    void should_returnGreatBritain_when_selectCountriesWithMoreThenOneCurrency() {
//	given:
	Country gb = setGiven2();

//	when:
	List<Country> countries = countryRepository.findCountriesWithTwoOrMoreCurrencies();

	Country country = countries.get(0);
//	then:
	Assertions.assertThat(country.getCountryName()).isEqualTo(gb.getCountryName());
	Assertions.assertThat(country.getCountryName()).isEqualTo(gb.getCountryName());
    }

    public Country setGiven2() {
	CurrencyInfo eur = new CurrencyInfo(CurrencyCode.EUR, "euro");
	CurrencyInfo gbp = new CurrencyInfo(CurrencyCode.GBP, "funt");

	Country pl = new Country("Poland");
	Country al = new Country("Albania");
	Country gb = new Country("Great Britain");

	currencyInfoRepository.add(gbp);
	currencyInfoRepository.add(eur);

	countryRepository.add(pl, CurrencyCode.EUR);
	countryRepository.add(al, CurrencyCode.EUR);
	countryRepository.add(gb, CurrencyCode.GBP);

	countryRepository.addCurrency("Great Britain", CurrencyCode.EUR);

//	countryRepository.add(new Country("Czech", "Prague"));
	return gb;
    }
    
    @Test
    void should_returnCountryByName() {
//	given:
	Country angola = new Country("Angola");
	countryRepository.add(angola);
	
//	when:
	Country findByName = countryRepository.findByName("Angola");
	
//	then:
	Assertions.assertThat(angola.getCountryName()).isEqualTo(findByName.getCountryName());
	
    }
    
}
