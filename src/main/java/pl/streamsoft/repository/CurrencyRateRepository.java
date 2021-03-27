package pl.streamsoft.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CurrencyConverter;
import pl.streamsoft.util.Observable;
import pl.streamsoft.util.Observer;

public class CurrencyRateRepository implements Observable, Repository<CurrencyRate, String> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private List<Observer> observers = new ArrayList<>();

    
    
    public CurrencyRateRepository() {
	addObserver(CurrencyConverter.cacheMap);
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
	addObserver(CurrencyConverter.cacheMap);
	closeTransaction();
	notifyObservers(find);
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
    public void addObserver(Observer observer) {
	this.observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
	this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object object) {
	observers.forEach(o -> o.update(object));
    }

}
