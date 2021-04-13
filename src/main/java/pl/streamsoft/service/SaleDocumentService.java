package pl.streamsoft.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import pl.streamsoft.dto.RateDifference;
import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.repository.CurrencyRateRepository;
import pl.streamsoft.util.CurrencyConverter;

public class SaleDocumentService {

    public void insert() {

	
	ConvertedAmount convertToPLN = new CurrencyConverter().convertToPLN(new AmountDataToConvert(BigDecimal.TEN, CurrencyCode.EUR, LocalDate.now().minusDays(17)));
	ConvertedAmount convertToPLN2 = new CurrencyConverter().convertToPLN(new AmountDataToConvert(BigDecimal.TEN, CurrencyCode.USD, LocalDate.now()));
	new CurrencyRateRepository().add(new CurrencyRate(CurrencyCode.UAH, "uah", new BigDecimal("4.5687"), LocalDate.now()));
	
	
	LocalDate start = LocalDate.of(2000, 1, 1);
	LocalDate end = LocalDate.now();
	CurrencyRateRepository currencyRateRepository = new CurrencyRateRepository();
	
	List<RateDifference> differenceInPeriod = currencyRateRepository.findCurrencyRateWithMaxDifferenceValueBetween(start, end, 3);
	List<CurrencyRate> maxVal = currencyRateRepository.findCurrencyRateWithMaxValueBetween(start, end, CurrencyCode.EUR);
	List<CurrencyRate> minVal = currencyRateRepository.findCurrencyRateWithMinValueBetween(start, end, CurrencyCode.IDR);
	List<CurrencyRate> find5 = currencyRateRepository.find5BestRatesForCurrency(CurrencyCode.EUR);

//	
//	
	CurrencyRate cr = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.6001"), LocalDate.now().minusDays(21));
	currencyRateRepository.remove(cr.getId());


    }
}