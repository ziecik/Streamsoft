package pl.streamsoft.util;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public class CSVParser {

	public static List<CurrencyRate> parseCSVFileToCurrencyRate(String filePath) throws IOException {
		List<CurrencyRate> currencyRates = new ArrayList<CurrencyRate>();

		CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build();

		List<String[]> readAll = csvReader.readAll();

		for (String[] string : readAll) {
			String[] fields = string[0].split(";");
			CurrencyRate currencyRate = new CurrencyRate(fields[0], CurrencyCode.valueOf(fields[1]),
					new BigDecimal(fields[2]), LocalDate.parse(fields[3]));
			currencyRates.add(currencyRate);
		}

		return currencyRates;
	}
}
