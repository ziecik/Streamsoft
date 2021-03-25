package pl.streamsoft.main;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRateRepository;

public class Main {

    public static void main(String[] args) {
//		new SaleDocumentService().insert();
	CurrencyRate entity = new CurrencyRate("dolar", CurrencyCode.GBP, new BigDecimal("5.4321"), LocalDate.now().minusDays(1));

	
//	new DBService().addObject(entity);
	
	CurrencyRate object =  new CurrencyRateRepository().find("GBP2021-03-24");
	System.out.println(object.getId());
    }

}
