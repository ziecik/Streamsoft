package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import model.CurrencyCode;
import model.CurrencyRate;

public class CSVParser {

	public static List<CurrencyRate> parseCSVFileToCurrencyRate(String filePath) {
		List<CurrencyRate> currencyRates = new ArrayList<CurrencyRate>();

		try {
			CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build();

			List<String[]> readAll = csvReader.readAll();

			for (String[] string : readAll) {
				String[] fields = string[0].split(";");
				CurrencyRate currencyRate = new CurrencyRate(fields[0], CurrencyCode.valueOf(fields[1]), new BigDecimal(fields[2]), LocalDate.parse(fields[3]));
				currencyRates.add(currencyRate);
			}
		

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currencyRates;
	}
}
