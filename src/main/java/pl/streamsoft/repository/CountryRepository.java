package pl.streamsoft.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.streamsoft.model.Country;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;

public class CountryRepository implements Repository<Country, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public void add(Country entity, CurrencyCode code) {
  	beginTransaction();

  	entityManager.persist(entity);
  	CurrencyInfo currency = entityManager.find(CurrencyInfo.class, code);
  	List<CurrencyInfo> currencies = entity.getCurrencies();
  	currency.getCountries().add(entity);
  	closeTransaction();
      }
    
    @Override
    public void add(Country entity) {
	beginTransaction();

	
	
	closeTransaction();
    }

    @Override
    public void remove(Long id) {
	// TODO Auto-generated method stub

    }

    @Override
    public Country find(Long id) {
	// TODO Auto-generated m stub
	return null;
    }

    @Override
    public void update(Long id, Country entity) {
	// TODO Auto-generated method stub

    }


    private void beginTransaction() {
	entityManagerFactory = Persistence.createEntityManagerFactory("dbCurrencyConnection");
	entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
    }

    private void closeTransaction() {
	entityManager.getTransaction().commit();
	entityManager.close();
	entityManagerFactory.close();
    }
}
