package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import pl.streamsoft.dto.RateDifference;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;
import pl.streamsoft.util.DC;

public class SaleDocumentService {

    public void insert() {

	LocalDate start = LocalDate.of(2000, 1, 1);
	LocalDate end = LocalDate.now();
	CurrencyRateRepository currencyRateRepository = new CurrencyRateRepository();
	
//	DC.convertFromAllCurrencies();
	
	List<RateDifference> differenceInPeriod = currencyRateRepository.findCurrencyRateWithMaxDifferenceValueBetween(start, end, 5);
	List<CurrencyRate> maxVal = currencyRateRepository.findCurrencyRateWithMaxValueBetween(start, end, CurrencyCode.EUR);
	List<CurrencyRate> minVal = currencyRateRepository.findCurrencyRateWithMinValueBetween(start, end, CurrencyCode.IDR);
	List<CurrencyRate> find5 = currencyRateRepository.find5BestRatesForCurrency(CurrencyCode.EUR);

	
	
	CurrencyRate cr = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.6001"), LocalDate.now().minusDays(21));
//	currencyRateRepository.remove(cr.getId());
//	new CurrencyInfoRepository().remove(CurrencyCode.EUR);


    }
}