package test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import pl.streamsoft.dto.Period;
import pl.streamsoft.dto.RateDifference;
import pl.streamsoft.model.Country;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyInfoRepository;
import pl.streamsoft.repository.CurrencyRateRepository;

public class DBQueriesTest {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    String persistenceUnitName = "dbH2Test";

    @Test
    void tsttConnection() {

	

//	given:	
	Country gb = setGiveForTest1();

//	when:
	String query = "select c from Country c join fetch c. currencies where size(c.currencies) >= 2";

	TypedQuery<Country> createQuery = entityManager.createQuery(query, Country.class);
	List<Country> countreis = createQuery.getResultList();
	Country country = countreis.get(0);
	
	
	entityManager.getTransaction().commit();
	entityManager.close();
	entityManagerFactory.close();

//	then:

	Assertions.assertThat(country.getCountryName()).isEqualTo(gb.getCountryName());
    }


    private Country setGiveForTest1() {
	CurrencyInfo eur = new CurrencyInfo(CurrencyCode.EUR, "euro");
	CurrencyInfo gbp = new CurrencyInfo(CurrencyCode.GBP, "euro");

	Country pl = new Country("Poland");
	Country es = new Country("Spain");
	Country gb = new Country("Great Britain");
	
	
	
	entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	
	
	entityManager.persist(gb);
	entityManager.persist(pl);
	entityManager.persist(es);
	
	entityManager.persist(eur);
	entityManager.persist(gbp);
	
	List<Country> countries = new ArrayList<>();
	countries.add(gb);
	countries.add(es);
	countries.add(pl);
	
	eur.setCountries(countries);
	
	List<Country> gbOnly = new ArrayList<>();
	gbOnly.add(gb);
	
	gbp.setCountries(gbOnly);
	
	
	
	entityManager.getTransaction().commit();
	entityManager.getTransaction().begin();
	return gb;
    }

    
    @Test
    void test2() {
	
//	given:
	setGivenForTest2();
	
//	when:
	String sqlQuery = "select i.code, i.currencyName, max(c.rateValue)- min(c.rateValue) from CurrencyRate c join CurrencyInfo i on c.currencyInfo = i where dateOfAnnouncedRate between :start and :end  group by i.code order by max(c.rateValue)-min(c.rateValue) desc";

	LocalDate start = LocalDate.now();
	LocalDate end = start.minusDays(5);
	
	TypedQuery<Object[]> query = entityManager.createQuery(sqlQuery, Object[].class);
	query.setParameter("start", start);
	query.setParameter("end", end);

	List<Object[]> currencyRates = query.setMaxResults(1).getResultList();

	List<RateDifference> differences = new ArrayList<>();
	Period period = new Period(start, end);
	for (Object[] objects : currencyRates) {
	    differences.add(new RateDifference((CurrencyCode) objects[0], (String) objects[1], (BigDecimal) objects[2],
		    period));
	}

	RateDifference rateDifference = differences.get(0);
	
	entityManager.getTransaction().commit();
	entityManager.close();
	entityManagerFactory.close();
//	then:
//	Assertions.assertThat(rateDifference.getCode()).isEqualTo(CurrencyCode.EUR);
	
    }


    private void setGivenForTest2() {
	
	
	CurrencyInfo eur = new CurrencyInfo(CurrencyCode.EUR, "euro");
	CurrencyInfo usd = new CurrencyInfo(CurrencyCode.USD, "dollar");
	
	CurrencyRate eur1 = new CurrencyRate(eur, new BigDecimal("4.1234"), LocalDate.now());
	CurrencyRate eur2 = new CurrencyRate(eur, new BigDecimal("4.1500"), LocalDate.now().minusDays(1));
	CurrencyRate eur3 = new CurrencyRate(eur, new BigDecimal("4.2234"), LocalDate.now().minusDays(2));
	
//	
//	CurrencyRate usd1 = new CurrencyRate("euro", CurrencyCode.USD, new BigDecimal("3.1234"), LocalDate.now());
//	CurrencyRate usd2 = new CurrencyRate("euro", CurrencyCode.USD, new BigDecimal("3.1700"), LocalDate.now().minusDays(1));
//	CurrencyRate usd3 = new CurrencyRate("euro", CurrencyCode.USD, new BigDecimal("3.2134"), LocalDate.now().minusDays(2));
//	CurrencyRate usd4 = new CurrencyRate("euro", CurrencyCode.USD, new BigDecimal("3.4234"), LocalDate.now().minusDays(4));
//	
	ArrayList<CurrencyRate> currencyRates = new ArrayList<>();
	currencyRates.add(eur3);
	currencyRates.add(eur2);
	currencyRates.add(eur1);
	eur.setCurrencyRates(currencyRates);
	
	eur1.setCurrencyRateInfo(eur);
	eur2.setCurrencyRateInfo(eur);
	eur3.setCurrencyRateInfo(eur);
//	
	
	entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	
	
	
	
	entityManager.persist(eur1);
	entityManager.persist(eur2);
	entityManager.persist(eur3);
//	
	
	entityManager.persist(eur);
	
	
	

//	
	
	entityManager.getTransaction().commit();
	entityManager.getTransaction().begin();
    }
    
}
