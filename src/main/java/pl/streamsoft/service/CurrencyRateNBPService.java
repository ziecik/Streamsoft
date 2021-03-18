package pl.streamsoft.service;

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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import pl.streamsoft.exception.CurrencyRateServiceUnavailableException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public class CurrencyRateNBPService implements CurrencyRateService {

	@Override
	public CurrencyRate getCurrencyRate(CurrencyCode CurrencyCode, LocalDate localDate) throws IOException {
		String json = getCurrencyRateJSONWihtProperDate(CurrencyCode, localDate);
		CurrencyRate currencyRate = parseJSONToCurrencyRateObject(CurrencyCode, localDate, json);
		return currencyRate;
	}

	private String getCurrencyRateJSONWihtProperDate(CurrencyCode CurrencyCode, LocalDate localDate) throws IOException {
		String inputLine = null;
		URLConnection connection;

		connection = getConnection(CurrencyCode, localDate);
		
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		inputLine = bufferedReader.readLine();

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

	private URLConnection getConnection(CurrencyCode CurrencyCode, LocalDate localDate) throws IOException {
		URLConnection connection = null;
		int responseCode = 0;
		int attempts = 7;
		for(int i = 0; i < attempts; i++) {
			if(responseCode != 200 ) {
			String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + CurrencyCode.toString().toLowerCase() + "/"
					+ localDate.toString() + "?format=json";
			URL url = new URL(apiURL);
			connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setRequestMethod("GET");
			responseCode = httpURLConnection.getResponseCode();
			localDate = localDate.minusDays(1);
		}} 
		
		if(connection == null)
			throw new CurrencyRateServiceUnavailableException();
		
		return connection;
	}

}
