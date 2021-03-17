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

import model.CurrencyCode;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class CurrencyUtil {

	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate date) {

		String strDate = date.toString();

		if (date.equals(LocalDate.now()) && LocalDateTime.now().isBefore(LocalDate.now().atTime(12, 0))) {
			strDate = "last/1";
		} else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			strDate = date.minusDays(2).toString();
		} else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			strDate = date.minusDays(1).toString();
		}

		String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + newCurrencyCode.toString().toLowerCase() + "/"
				+ strDate + "?format=json";

		JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

		BigDecimal result = null;

		try {
			URL url = new URL(apiURL);
			URLConnection connection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));

			String inputLine;
			if ((inputLine = bufferedReader.readLine()) != null) {
				JSONObject exchangeRate = (JSONObject) jsonParser.parse(inputLine);
				JSONArray rates = (JSONArray) exchangeRate.get("rates");
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

	public static BigDecimal convertToPLN(BigDecimal valueToConvert, CurrencyCode newCurrencyCode) {
		return convertToPLN(valueToConvert, newCurrencyCode, LocalDate.now());
	}

}
