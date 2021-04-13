package pl.streamsoft.main;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.SaleDocumentService;

public class Main {

    public static void main(String[] args) {
//	new SaleDocumentService().insert();
	
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dbCurrencyConnection");
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	Session session = entityManager.unwrap(Session.class);
	
	CurrencyRate currencyRate = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.2135"), LocalDate.of(2000, 1, 1));
	currencyRate.setCurrencyName("ojro");
	
	session.beginTransaction();
	
	Serializable s1 = session.save(currencyRate);
	
	
	session.evict(currencyRate);
	
	
	EntityManager delegate = (EntityManager) session.getDelegate();
	
	System.out.println(entityManager.equals(delegate));
	session.flush();
    }

}
