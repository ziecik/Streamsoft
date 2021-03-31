package pl.streamsoft.repository;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.model.CurrencyRateUpdater;
import pl.streamsoft.util.CurrencyRateSource;

public class CurrencyRateRepository
	implements Repository<CurrencyRate, String>, CurrencyRateSource, CurrencyRateUpdater {
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
	if (find != null) {
	    find.setRateValue(entity.getRateValue());
	} else {
	    entityManager.persist(entity);
	}
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
	return Optional.ofNullable(find).orElseThrow(() -> new DataNotFoundException("Data not found in db"));
    }

    @Override
    public void update(CurrencyRate currencyRate) {
	update(currencyRate.getId(), currencyRate);
    }

}
