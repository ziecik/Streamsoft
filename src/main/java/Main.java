import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.CurrencyCode;
import model.CurrencyRate;

public class Main {

	public static void main(String[] args) {

//		List<CurrencyRate> currentCurrencyRates = CurrencyUtil.getCurrentCurrencyRates();
//		new SaleDocumentService().insert();

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("menuItems");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		CurrencyRate currencyRate = new CurrencyRate();
		currencyRate.setCode(CurrencyCode.EUR);
		currencyRate.setCurrencyName("ojro");
		
		
		entityManager.getTransaction().begin();

		

		entityManager.persist(currencyRate);

		TypedQuery<CurrencyRate> typedQuery = entityManager.createNamedQuery("SELECT e FROM currencyRate e", CurrencyRate.class);
		
		entityManager.createQuery("SELECT e FROM currencyRate e", CurrencyRate.class).getSingleResult();

		
		CurrencyRate find = entityManager.find(CurrencyRate.class, 1L);

//		entityManager.refresh(currencyRate);;
		entityManager.getTransaction().commit();
		
		
		
		
		
		entityManager.close();
		entityManagerFactory.close();
	}

}
