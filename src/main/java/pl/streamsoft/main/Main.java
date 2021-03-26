package pl.streamsoft.main;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.CurrencyRateRepository;
import pl.streamsoft.service.SaleDocumentService;

public class Main {

    public static void main(String[] args) {
		new SaleDocumentService().insert();
	CurrencyRate entity = new CurrencyRate("dolar", CurrencyCode.GBP, new BigDecimal("5.5555"), LocalDate.now().minusDays(1));

	CurrencyRateRepository currencyRateRepository = new CurrencyRateRepository();
	
//	/currencyRateRepository.add(entity);
	
	
	CurrencyRate find = currencyRateRepository.find("GBP2021-03-25");

	currencyRateRepository.update("GBP2021-03-25", entity);
	
	System.out.println(find.getId());
	
//	currencyRateRepository.remove("GBP2021-03-24");
    }

}
