package pl.streamsoft.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import pl.streamsoft.model.Country;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;

public class CountryRepository implements Repository<Country, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private String persistenceUnitName;

    
    
    public CountryRepository() {
	super();
	this.persistenceUnitName = "dbCurrencyConnection";
    }
    
    

    public CountryRepository(String persistenceUnitName) {
	super();
	this.persistenceUnitName = persistenceUnitName;
    }



    public void add(Country entity, CurrencyCode code) {
  	beginTransaction();
  	entityManager.persist(entity);
  	CurrencyInfo currency = entityManager.find(CurrencyInfo.class, code);
//  	List<CurrencyInfo> currencies = entity.getCurrencies();
  	
  	currency.getCountries().add(entity);
  	closeTransaction();
      }
    
    public void addCurrency(Long id, CurrencyCode code) {
	beginTransaction();
	
	Country country = entityManager.find(Country.class, id);
	CurrencyInfo currencyInfo = entityManager.find(CurrencyInfo.class, code);
	
	currencyInfo.getCountries().add(country);
	
//	country.addCurrency(currencyInfo);
	
	closeTransaction();
    }
    
    @Override
    public void add(Country entity) {
	beginTransaction();
	entityManager.persist(entity);
	
	
	closeTransaction();
    }

    @Override
    public void remove(Long id) {
	beginTransaction();
	Country find;
	if((find = entityManager.find(Country.class, id)) != null)
	    entityManager.remove(find);
	closeTransaction();
    }

    public Country findByName(String countryName) {
	beginTransaction();
	String sql = "select c from Country c where c.countryName =:countryName";
	TypedQuery<Country> query = entityManager.createQuery(sql, Country.class);
	query.setParameter("countryName", countryName);
	List<Country> countries = query.getResultList();
	
	Country country = countries.get(0);
	closeTransaction();
	
	return country;
    }
    
    @Override
    public Country find(Long id) {
	Country country = entityManager.find(Country.class, id);
	return country;
    }

    @Override
    public void update(Long id, Country entity) {
	beginTransaction();
	
	Country find = find(id);
	entityManager.detach(find);
	
	entityManager.merge(find);
	
	closeTransaction();

    }

//    #4
    public List<Country> findCountriesWithTwoOrMoreCurrencies() {
	beginTransaction();
	
	String query = "select c from Country c join fetch c. currencies where size(c.currencies) >= 2";
	
	TypedQuery<Country> createQuery = entityManager.createQuery(query, Country.class);
	List<Country> countreis = createQuery.getResultList();
	
	closeTransaction();
	return countreis;
	
    }
    

    private void beginTransaction() {
	
	entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
    }

    private void closeTransaction() {
	entityManager.getTransaction().commit();
	entityManager.close();
	entityManagerFactory.close();
    }
}
