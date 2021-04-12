package pl.streamsoft.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.streamsoft.model.Country;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyInfo;
import pl.streamsoft.repository.CountryRepository;
import pl.streamsoft.repository.CurrencyInfoRepository;
import pl.streamsoft.service.SaleDocumentService;

public class Main {

    public static void main(String[] args) {
//	new SaleDocumentService().insert();
		
	
	CurrencyInfoRepository currencyInfoRepository = new CurrencyInfoRepository();
	CountryRepository countryRepository = new CountryRepository();
	
	
	countryRepository.add(new Country("Andora", new ArrayList<CurrencyInfo>()), CurrencyCode.EUR);
    }

}
