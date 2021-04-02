package pl.streamsoft.main;

import java.time.LocalDate;
import java.util.List;

import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;
import pl.streamsoft.service.SaleDocumentService;

public class Main {

    public static void main(String[] args) {
	new SaleDocumentService().insert();
	
	CurrencyRateRepository currencyRateRepository = new CurrencyRateRepository();
	List<CurrencyRate> findCurrencyRateWithMinValueBetween = currencyRateRepository.findCurrencyRateWithMaxValueBetween(LocalDate.of(2000, 1, 1), LocalDate.now());
	List<Object[]> diffrence = currencyRateRepository.findCurrencyRateWithMaxDifferenceValueBetween(LocalDate.of(2000, 1, 1), LocalDate.now());
	findCurrencyRateWithMinValueBetween.stream().forEach(System.out::println);
    }

}
