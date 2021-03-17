package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.CurrencyCode;
import model.CurrencyRate;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class CurrencyUtil{

	public static List<CurrencyRate> getCurrentCurrencyRates() {

		String apiURL = "http://api.nbp.pl/api/exchangerates/tables/a?format=json";
		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

		BigDecimal currencyMidValue;
		CurrencyRate currencyRate;

		List<CurrencyRate> currentCurrencyRates= new ArrayList<>();
		try {
			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));

			String inputLine;
			if ((inputLine = bufferedReader.readLine()) != null) {

				JSONArray jsonArray = (JSONArray) jsonParser.parse(inputLine);
				JSONObject exchangeRatesTable = (JSONObject) jsonArray.get(0);
				JSONArray rates = (JSONArray) exchangeRatesTable.get("rates");
				LocalDate localDate = LocalDate.parse((String)exchangeRatesTable.get("effectiveDate"));

				for (Object rate : rates) {

					String currencyName = (String) ((JSONObject) rate).get("currency");
					CurrencyCode code = CurrencyCode.valueOf((String) ((JSONObject) rate).get("code"));
					BigDecimal mid = new BigDecimal(((JSONObject)rate).get("mid").toString());


					currencyMidValue = mid.setScale(4, RoundingMode.DOWN);
					
					currencyRate = new CurrencyRate();
					currencyRate.setCurrencyName(currencyName);
					currencyRate.setCode(code);
					currencyRate.setRateValue(currencyMidValue);
					currencyRate.setLocalDate(localDate);

					currentCurrencyRates.add(currencyRate);
				}
			}

		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		return currentCurrencyRates;
	}

	
	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate localDate) {
		
		String strDate = localDate.toString();
		
		if(localDate.equals(LocalDate.now()) && LocalDateTime.now().isBefore(LocalDate.now().atTime(12, 0))) {
			strDate = "last/1";
		} else if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
			strDate = localDate.minusDays(2).toString();
		} else if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
			strDate = localDate.minusDays(1).toString();
		}
		
		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + newCurrencyCode.toString().toLowerCase() + "/" + strDate +"?format=json";
		
		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
		
		BigDecimal result = null;
		
		try {
			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));

			String inputLine;
			if ((inputLine = bufferedReader.readLine()) != null) {
				JSONObject exchangeRate = (JSONObject) jsonParser.parse(inputLine);
				JSONArray rates = (JSONArray)exchangeRate.get("rates");
				JSONObject rate = (JSONObject) rates.get(0);
				BigDecimal mid = new BigDecimal(rate.getAsString("mid").toString());
				
				result = mid.multiply(valueToConvert).setScale(2, RoundingMode.DOWN);
				
			}

		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}

		return result;
	}

	public static CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate localDate) {

		String strDate = localDate.toString();
		CurrencyRate currencyRate = new CurrencyRate();


		if(localDate.equals(LocalDate.now()) && LocalDateTime.now().isBefore(LocalDate.now().atTime(12, 0))) {
			strDate = "last/1";
		} else if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
			strDate = localDate.minusDays(2).toString();
		} else if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
			strDate = localDate.minusDays(1).toString();
		}

		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode.toString().toLowerCase() + "/" + strDate +"?format=json";

		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

		try {
			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));

			String inputLine;
			if ((inputLine = bufferedReader.readLine()) != null) {
				JSONObject exchangeRate = (JSONObject) jsonParser.parse(inputLine);
				JSONArray rates = (JSONArray)exchangeRate.get("rates");
				JSONObject rate = (JSONObject) rates.get(0);
				BigDecimal rateValue = new BigDecimal(rate.getAsString("mid").toString());
				String currencyName = (String) ((JSONObject) exchangeRate).get("currency");

				currencyRate.setRateValue(rateValue);
				currencyRate.setLocalDate(localDate);
				currencyRate.setCode(currencyCode);
				currencyRate.setCurrencyName(currencyName);

			}


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}

		return currencyRate;
	}
}
