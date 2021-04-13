package pl.streamsoft.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pl.streamsoft.dto.Period;
import pl.streamsoft.dto.RateDifference;
import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.model.CurrencyRateUpdater;
import pl.streamsoft.util.CurrencyRateSource;

public class CurrencyRateRepository
	implements Repository<CurrencyRate, String>, CurrencyRateSource, CurrencyRateUpdater {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    String persistenceUnitName;

    public CurrencyRateRepository() {
	this.persistenceUnitName = "dbCurrencyConnection";
	entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public CurrencyRateRepository(String persistenceUnitName) {
	super();
	this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    public void add(CurrencyRate entity) {
	beginTransaction();

	addOrUpdate(entity.getId(), entity);

	closeTransaction();
    }

    @Override
    public void remove(String id) {
	beginTransaction();
//	CurrencyRate find = entityManager.find(CurrencyRate.class, id);
//	entityManager.remove(find);
//	
	Query deleteQuery = entityManager.createQuery("delete from CurrencyRate c where c.id = :id");
	deleteQuery.setParameter("id", id);

	deleteQuery.executeUpdate();
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

	addOrUpdate(id, entity);

	closeTransaction();
    }

    public void addOrUpdate(String id, CurrencyRate entity) {
	CurrencyRate find = entityManager.find(CurrencyRate.class, id);
	if (find != null) {
	    find.setRateValue(entity.getRateValue());
	} else {
	    entityManager.persist(entity);
	}

	CurrencyInfo cruenncyInfo = entityManager.find(CurrencyInfo.class, entity.getCode());
	if (cruenncyInfo != null) {
	    entity.setCurrencyInfo(cruenncyInfo);
	} else {
	    CurrencyInfo currencyInfo = new CurrencyInfo(entity.getCode(), entity.getCurrencyName());
	    entity.setCurrencyInfo(currencyInfo);
	    entityManager.persist(currencyInfo);
	}
    }

//	max difference in period
//	#1
    public List<RateDifference> findCurrencyRateWithMaxDifferenceValueBetween(LocalDate start, LocalDate end,
	    int limit) {
	beginTransaction();

	String sqlQuery = "select i.code, i.currencyName, max(c.rateValue)- min(c.rateValue) from CurrencyRate c join CurrencyInfo i on c.currencyInfo = i where dateOfAnnouncedRate between :start and :end  group by i.code order by max(c.rateValue)-min(c.rateValue) desc";

	TypedQuery<Object[]> query = entityManager.createQuery(sqlQuery, Object[].class);
	query.setParameter("start", start);
	query.setParameter("end", end);

	List<Object[]> currencyRates = query.setMaxResults(limit).getResultList();

	List<RateDifference> differences = new ArrayList<>();
	Period period = new Period(start, end);
	for (Object[] objects : currencyRates) {
	    differences.add(new RateDifference((CurrencyCode) objects[0], (String) objects[1], (BigDecimal) objects[2],
		    period));
	}

	closeTransaction();
	return differences;
    }

//	find minimum value in period
//	#2
    public List<CurrencyRate> findCurrencyRateWithMinValueBetween(LocalDate start, LocalDate end, CurrencyCode code) {
	beginTransaction();

	String sqlQuery = "\r\n"
		+ "select c from CurrencyRate c join fetch c.currencyInfo i where c.currencyInfo.code = :code and ratevalue in (select min(x.rateValue) as minval from CurrencyRate x \r\n"
		+ "where dateofannouncedrate between :start and :end  group by code) order by ratevalue asc ";

	TypedQuery<CurrencyRate> query = entityManager.createQuery(sqlQuery, CurrencyRate.class);
	query.setParameter("start", start);
	query.setParameter("end", end);
	query.setParameter("code", code);
//	
	List<CurrencyRate> currencyRates = query.getResultList();

	closeTransaction();
	return currencyRates;
    }

//	find max value in period
//	#2
    public List<CurrencyRate> findCurrencyRateWithMaxValueBetween(LocalDate start, LocalDate end, CurrencyCode code) {
	beginTransaction();

	String sqlQuery = "\r\n"
		+ "select c from CurrencyRate c join fetch c.currencyInfo i where c.currencyInfo.code = :code and ratevalue in (select max(x.rateValue) from CurrencyRate x \r\n"
		+ "where dateofannouncedrate between :start and :end  group by code) order by ratevalue desc ";

	TypedQuery<CurrencyRate> query = entityManager.createQuery(sqlQuery, CurrencyRate.class);
	query.setParameter("start", start);
	query.setParameter("end", end);
	query.setParameter("code", code);
//	
	List<CurrencyRate> currencyRates = query.getResultList();

	closeTransaction();
	return currencyRates;
    }

//	#3
    public List<CurrencyRate> find5BestRatesForCurrency(CurrencyCode code) {
	beginTransaction();
	String sqlQuery = "select c from CurrencyRate c join fetch c.currencyInfo i where c.currencyInfo.code = :code \r\n"
		+ "  order by ratevalue desc";

	TypedQuery<CurrencyRate> query = entityManager.createQuery(sqlQuery, CurrencyRate.class);

	query.setParameter("code", code);

	List<CurrencyRate> currencyRates = query.setMaxResults(5).getResultList();

	closeTransaction();
	return currencyRates;
    }

//	#3
    public List<CurrencyRate> find5WorstForCurrency(CurrencyCode code) {
	beginTransaction();
	String sqlQuery = "select c from CurrencyRate c join fetch c.currencyInfo i where c.currencyInfo.code = :code \r\n"
		+ "  order by ratevalue";

	TypedQuery<CurrencyRate> query = entityManager.createQuery(sqlQuery, CurrencyRate.class);

	query.setParameter("code", code);

	List<CurrencyRate> currencyRates = query.setMaxResults(5).getResultList();

	closeTransaction();
	return currencyRates;
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
