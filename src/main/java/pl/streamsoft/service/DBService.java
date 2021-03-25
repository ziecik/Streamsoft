package pl.streamsoft.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import pl.streamsoft.model.CurrencyRate;

public class DBService {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public void addObject(Object object) {
	beginTransaction();
	entityManager.persist(object);
	closeTransaction();
    }
    
    public void removeObject(Object object) {
	beginTransaction();
	entityManager.remove(object);
	closeTransaction();
    }
    
    public CurrencyRate getObject(String id, Class<? extends Object> clazz) {
	beginTransaction();
	TypedQuery<CurrencyRate > createQuery = entityManager.createQuery("select c from CurrencyRate c where c.id = :id", CurrencyRate.class);
	createQuery.setParameter("id", id);
	CurrencyRate singleResult = createQuery.getSingleResult();
	closeTransaction();
	return singleResult;
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
