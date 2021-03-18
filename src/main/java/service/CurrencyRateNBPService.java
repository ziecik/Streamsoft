package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.apache.commons.collections.FunctorException;

import exception.FutureDataException;
import model.CurrencyCode;
import model.CurrencyRate;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class CurrencyRateNBPService implements CurrencyRateService {

	@Override
	public CurrencyRate getCurrencyRate(CurrencyCode CurrencyCode, LocalDate localDate) {

		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + CurrencyCode.toString().toLowerCase() + "/"
				+ localDate.toString() + "?format=json";

		String json = getCurrencyRateJSONWihtProperDate(CurrencyCode, localDate, apiURL);
		CurrencyRate currencyRate = parseJSONToCurrencyRateObject(CurrencyCode, localDate, json);

		return currencyRate;
	}

	private String getCurrencyRateJSONWihtProperDate(CurrencyCode CurrencyCode, LocalDate localDate, String apiURL) {
		int responseCode;
		String inputLine = null;
		URLConnection connection;
		
		if(isDataValid(localDate))
		try {
			do {
				URL url = new URL(apiURL);
				connection = url.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
				httpURLConnection.setRequestMethod("GET");
				responseCode = httpURLConnection.getResponseCode();
				localDate = localDate.minusDays(1);
			} while (responseCode != 200);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			inputLine = bufferedReader.readLine();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		else {
			System.out.println("Futuredata - rates not known");
		}
		
		return inputLine;
	}

	private CurrencyRate parseJSONToCurrencyRateObject(CurrencyCode CurrencyCode, LocalDate localDate,
			String inputLine) {
		
		CurrencyRate result = null;
		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
		JSONObject exchangeRate;

		try {
			exchangeRate = (JSONObject) jsonParser.parse(inputLine);
			JSONArray rates = (JSONArray) exchangeRate.get("rates");
			JSONObject rate = (JSONObject) rates.get(0);
			BigDecimal rateValue = new BigDecimal(rate.getAsString("mid").toString());
			String currencyName = (String) ((JSONObject) rate).get("currency");
			result = new CurrencyRate(currencyName, CurrencyCode, rateValue, localDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	private boolean isDataValid(LocalDate date) {
		if(date.isBefore(LocalDate.now())) {
			return true;
		} else {
			throw new FutureDataException("There is no future currency rate");
		}
	}

}
