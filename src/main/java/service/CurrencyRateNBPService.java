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

		
		int responseCode;
		CurrencyRate result = null;

		try {
			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setRequestMethod("GET");
			responseCode = httpURLConnection.getResponseCode();
			System.out.println(responseCode);
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

			String inputLine= bufferedReader.readLine();
			if ((inputLine ) != null) {
				result = parseJSONToCurrencyRateObject(CurrencyCode, localDate, inputLine);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}


	private CurrencyRate parseJSONToCurrencyRateObject(CurrencyCode CurrencyCode, LocalDate localDate, String inputLine)
			throws ParseException {
		CurrencyRate result;
		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
		JSONObject exchangeRate = (JSONObject) jsonParser.parse(inputLine);
		JSONArray rates = (JSONArray) exchangeRate.get("rates");
		JSONObject rate = (JSONObject) rates.get(0);
		BigDecimal rateValue = new BigDecimal(rate.getAsString("mid").toString());
		String currencyName = (String) ((JSONObject) rate).get("currency");

		result = new CurrencyRate(currencyName, CurrencyCode, rateValue, localDate);
		
		return result;
	}

	
	private static LocalDate validatekDate(LocalDate localDate) throws IOException {
		int responseCode;
		do {
			URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/a/" + localDate.toString());
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setRequestMethod("GET");
			responseCode = httpURLConnection.getResponseCode();
			localDate = localDate.minusDays(1);
		} while (responseCode == 200);

		return localDate;
	}
	
}
