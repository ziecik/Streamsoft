package pl.streamsoft.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.service.DataToObjectConverter;

public class JSONConverterNBP implements DataToObjectConverter {

    @Override
    public CurrencyRate convertToCurrencyRate(String data) {

	JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

	try {
	    JSONObject exchangeRate = (JSONObject) jsonParser.parse(data);
	    JSONArray rates = (JSONArray) exchangeRate.get("rates");
	    String currencyName = (String) exchangeRate.get("currency");
	    JSONObject rate = (JSONObject) rates.get(0);

	    BigDecimal rateValue = new BigDecimal(rate.getAsString("mid"));
	    LocalDate localDate = LocalDate.parse(rate.getAsString("effectiveDate"));
	    CurrencyCode currencyCode = (CurrencyCode) ((JSONObject) rate).get("code");
	    CurrencyRate currencyRate = new CurrencyRate(currencyName, currencyCode, rateValue, localDate);

	    return currencyRate;
	} catch (ParseException e) {
	    throw new DataNotFoundException("Parsing with parser: " + e.getClass().getName() + " failed", e);
	}
    }

}
