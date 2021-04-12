package pl.streamsoft.main;

import java.util.List;

import pl.streamsoft.model.Country;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.repository.CountryRepository;
import pl.streamsoft.repository.CurrencyInfoRepository;
import pl.streamsoft.service.SaleDocumentService;

public class Main {

    public static void main(String[] args) {
	new SaleDocumentService().insert();
	
		
	
//	CurrencyInfoRepository currencyInfoRepository = new CurrencyInfoRepository();
//	CountryRepository countryRepository = new CountryRepository();	
//	
//	countryRepository.add(new Country("USA"));
//	
//	countryRepository.addCurrency(81L, CurrencyCode.USD);
	
//	List<Country> countriesWithMoreThenOneCurrency = countryRepository.findCountriesWithTwoOrMoreCurrencies();
//	
//	countryRepository.add(new Country("Czech", "Prague"),  CurrencyCode.CZK);

//	countryRepository.remove(91L);
    }

}
