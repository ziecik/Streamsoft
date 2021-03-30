package pl.streamsoft.repository;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CurrencyRateSource;

public class CurrencyRateRepository implements Repository<CurrencyRate, String>, CurrencyRateSource {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public CurrencyRateRepository() {
    }

    @Override
    public void add(CurrencyRate entity) {
	beginTransaction();
	entityManager.persist(entity);
	closeTransaction();
    }

    @Override
    public void remove(String id) {
	beginTransaction();
	CurrencyRate find = entityManager.find(CurrencyRate.class, id);
	entityManager.remove(find);
	closeTransaction();
    }

    @Override
    public CurrencyRate find(String id) {
	beginTransaction();
	CurrencyRate find = entityManager.find(CurrencyRate.class, id);
	closeTransaction();
	return find;
    }

    @Override
    public void update(String id, CurrencyRate entity) {
	beginTransaction();
	CurrencyRate find = entityManager.find(CurrencyRate.class, id);
	find.setRateValue(entity.getRateValue());
	closeTransaction();
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

    @Override
    public CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion) {
	CurrencyRate find = find(currencyCode.toString() + dateOfConversion.toString());
	return find;
    }

}
