package main;


import java.util.List;

import model.CurrencyRate;
import service.SaleDocumentService;
import util.CSVParser;

public class Main {

	public static void main(String[] args) {
		new SaleDocumentService().insert();
//		List<CurrencyRate> parseCSVFileToCurrencyRate = CSVParser.parseCSVFileToCurrencyRate("src/main/resources/currencyRates.csv");
	}

}
