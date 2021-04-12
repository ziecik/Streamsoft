package pl.streamsoft.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;

public class CurrencyInfoRepository implements Repository<CurrencyInfo, CurrencyCode> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private String persistenceUnitName;

    public CurrencyInfoRepository() {
	super();
	this.persistenceUnitName = "dbCurrencyConnection";
	// TODO Auto-generated constructor stub
    }

    public CurrencyInfoRepository(String persistenceUnitName) {
	super();
	this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    public void add(CurrencyInfo entity) {
	beginTransaction();
	CurrencyInfo find = entityManager.find(CurrencyInfo.class, entity.getCode());
	if (find == null) {
	    entityManager.persist(entity);
	}
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
