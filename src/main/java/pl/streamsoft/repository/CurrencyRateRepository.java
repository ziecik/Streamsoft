package pl.streamsoft.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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

    // find max value in period
    public List<CurrencyRate> findCurrencyRateWithMaxValueBetween(LocalDate start, LocalDate end) {
	beginTransaction();

	String sqlQuery = "\r\n"
		+ "select c from currencyrate c where ratevalue in (select max(x.rateValue) as maxval from currencyrate x \r\n"
		+ "where dateofannouncedrate between :start and :end  group by code, currencyname) order by ratevalue desc ";

	TypedQuery<CurrencyRate> query = entityManager.createQuery(sqlQuery, CurrencyRate.class);
	query.setParameter("start", start);
	query.setParameter("end", end);
//	
	List<CurrencyRate> currencyRates = query.setMaxResults(5).getResultList();

	closeTransaction();
	return currencyRates;
    }

    // find minimum value in period
    public List<CurrencyRate> findCurrencyRateWithMinValueBetween(LocalDate start, LocalDate end) {
	beginTransaction();

	String sqlQuery = "\r\n"
		+ "select c from currencyrate c where ratevalue in (select min(x.rateValue) from currencyrate x \r\n"
		+ "where dateofannouncedrate between :start and :end  group by code, currencyname) order by ratevalue asc ";

	TypedQuery<CurrencyRate> query = entityManager.createQuery(sqlQuery, CurrencyRate.class);
	query.setParameter("start", start);
	query.setParameter("end", end);
//	
	List<CurrencyRate> currencyRates = query.setMaxResults(5).getResultList();

	closeTransaction();
	return currencyRates;
    }

//    max difference in period

    public List<Object[]> findCurrencyRateWithMaxDifferenceValueBetween(LocalDate start, LocalDate end) {
	beginTransaction();
	
	
	String sqlQuery = "select code, max(c.rateValue)- min(c.rateValue) from currencyrate c where dateofannouncedrate between :start and :end  group by c.code order by max(c.rateValue)-min(c.rateValue) desc";

	
	TypedQuery<Object[]> query = entityManager.createQuery(sqlQuery, Object[].class);
	query.setParameter("start", start);
	query.setParameter("end", end);
//	
	List<Object[]> currencyRates = query.setMaxResults(5).getResultList();  
		 
	
	closeTransaction();
	return currencyRates;
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
