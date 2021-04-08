package pl.streamsoft.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;

public class CurrencyInfoRepository implements Repository<CurrencyInfo, CurrencyCode> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Override
    public void add(CurrencyInfo entity) {
	beginTransaction();
	entityManager.persist(entity);
	closeTransaction();
    }

    @Override
    public void remove(CurrencyCode id) {
	beginTransaction();
	CurrencyInfo find = entityManager.find(CurrencyInfo.class, id);
	entityManager.remove(find);
	closeTransaction();
    }

    @Override
    public CurrencyInfo find(CurrencyCode id) {
	beginTransaction();
	CurrencyInfo find = entityManager.find(CurrencyInfo.class, id);
	closeTransaction();
	return find;
    }

    @Override
    public void update(CurrencyCode id, CurrencyInfo entity) {
	beginTransaction();
	CurrencyInfo find = entityManager.find(CurrencyInfo.class, id);
	find.setCurrencyName(entity.getCurrencyName());
	entityManager.persist(find);
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

}
