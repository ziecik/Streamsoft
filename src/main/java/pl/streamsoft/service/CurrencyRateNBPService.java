package pl.streamsoft.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public class CurrencyRateNBPService implements CurrencyRateService {

	@Override
	public CurrencyRate getCurrencyRate(CurrencyCode CurrencyCode, LocalDate localDate) throws IOException {

		String json = getCurrencyRateJSON(CurrencyCode, localDate);
		CurrencyRate currencyRate = parseJSONToCurrencyRateObject(CurrencyCode, localDate, json);
	
		return currencyRate;
	}

	private String getCurrencyRateJSON(CurrencyCode CurrencyCode, LocalDate localDate) throws IOException {

		URLConnection connection = getConnection(CurrencyCode, localDate);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		String inputLine = bufferedReader.readLine();
		bufferedReader.close();
		
		return inputLine;

	}

	private URLConnection getConnection(CurrencyCode CurrencyCode, LocalDate localDate) throws IOException {

		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + CurrencyCode.toString().toLowerCase() + "/"
				+ localDate.toString() + "?format=json";
		URL url = new URL(apiURL);
		URLConnection connection = url.openConnection();

		return connection;
	}

	private CurrencyRate parseJSONToCurrencyRateObject(CurrencyCode CurrencyCode, LocalDate localDate,
			String inputLine) {

		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

		try {
			JSONObject exchangeRate = (JSONObject) jsonParser.parse(inputLine);
			JSONArray rates = (JSONArray) exchangeRate.get("rates");
			JSONObject rate = (JSONObject) rates.get(0);
			BigDecimal rateValue = new BigDecimal(rate.getAsString("mid"));
			String currencyName = (String) ((JSONObject) rate).get("currency");
			CurrencyRate currencyRate = new CurrencyRate(currencyName, CurrencyCode, rateValue, localDate);
			
			return currencyRate;
		} catch (ParseException e) {
			throw new DataNotFoundException(e);
		}	
	}

}
