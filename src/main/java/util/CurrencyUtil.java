package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

				for (Object rate : rates) {

					String currencyName = (String) ((JSONObject) rate).get("currency");
					String code = (String) ((JSONObject) rate).get("code");
					BigDecimal mid = new BigDecimal(((JSONObject)rate).get("mid").toString());
					
					currencyMidValue = mid.setScale(4, RoundingMode.DOWN);
					
					currencyRate = new CurrencyRate();
					currencyRate.setCurrencyName(currencyName);
					currencyRate.setCode(code);
					currencyRate.setRate(currencyMidValue);

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
	
	public static List<String> getCountriesWithMoreThenOneCurrency() {
		List<String> countriesWithMoreThenOneCurrency = new ArrayList<>();
		
//		getCurrentCurrencyRates().stream().filter(c -> c.)
		
		
		return countriesWithMoreThenOneCurrency;
	}
	
	public static BigDecimal convertToPlnWithLAmbdaFilter(BigDecimal valueToConvert, CurrencyCode newCurrencyCode) {
		
		CurrencyRate currencyRate = (getCurrentCurrencyRates().stream().filter(c -> c.getCode().equals(newCurrencyCode.toString()))).collect(Collectors.toList()).get(0);
		BigDecimal result = currencyRate.getRate().multiply(valueToConvert).setScale(2, RoundingMode.HALF_UP);
		
		return result;

	}
	
	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode) {
		return convertToPLN(valueToConvert, newCurrencyCode, LocalDate.now() );
	}
	
	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate date) {
		
		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + newCurrencyCode.toString().toLowerCase() + "/" + date +"?format=json";
		
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

	public static BigDecimal getMinValue(CurrencyCode currencyCode, LocalDate start, LocalDate end) {
	
		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode.toString().toLowerCase() + "/" + start  + "/" + end  +"?format=json";
		
		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
		
		BigDecimal result = null;
		List<BigDecimal> collect = null;

		
		try {
			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));	
			String inputLine;
			
			if ((inputLine = bufferedReader.readLine()) != null) {
				
				JSONObject exchangeRate = (JSONObject) jsonParser.parse(inputLine);
				
				JSONArray rates = (JSONArray) exchangeRate.get("rates");
				
				collect = new ArrayList<>();
				collect = rates.stream().map(r -> new BigDecimal(((JSONObject)r).get("mid").toString())).collect(Collectors.toList());

				Collections.sort(collect);				
			}

		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		result = collect.get(collect.size() -1);
		
		
		return result;
	}
}
