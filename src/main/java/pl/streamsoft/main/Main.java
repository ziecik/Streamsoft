package pl.streamsoft.main;


import java.util.List;

import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.SaleDocumentService;
import pl.streamsoft.util.CSVParser;

public class Main {

	public static void main(String[] args) {
		new SaleDocumentService().insert();
//		List<CurrencyRate> parseCSVFileToCurrencyRate = CSVParser.parseCSVFileToCurrencyRate("src/main/resources/currencyRates.csv");
	}

}
