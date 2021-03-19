package pl.streamsoft.service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public class CurrencyRateCSVFIleService implements CurrencyRateService {

	@Override
	public CurrencyRate getCurrencyRate(CurrencyCode newCurrencyCode, LocalDate localDate) throws IOException {
		
		
		CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
		CSVReader csvReader = new CSVReaderBuilder(new FileReader("C://Users/jaroslaw.ziecik/eclipse-workspace/Streamsoft/src/main/resources/currencyRates.csv"))
				.withCSVParser(csvParser).withSkipLines(1).build();
		List<String[]> readAll = csvReader.readAll();
		
		CurrencyRate currencyRate = readAll.stream().map(s -> new CurrencyRate(s[0], CurrencyCode.valueOf(s[1]), new BigDecimal(s[2]), LocalDate.parse(s[3]))).filter(cr -> cr.getCode().equals(newCurrencyCode)).collect(Collectors.toList()).get(0);
			
		return currencyRate;
	}

}
