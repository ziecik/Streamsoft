package pl.streamsoft.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;

public class CurrencyRateProviderNBP implements CurrencyRateProvider {

    @Override
    public String getCurrencyRateData(CurrencyCode currencyCode, LocalDate localDate) {

	if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY || localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
	    new DataNotFoundException("Data not found by " + CurrencyRateProviderNBP.class.getName() + " on date "
		    + localDate.toString());
	}
	// Connection
	String apiURL = "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode.toString().toLowerCase() + "/"
		+ localDate.toString() + "?format=json";
	String providedData;
	try {
	    URL url = new URL(apiURL);
	    URLConnection connection = url.openConnection();
	    BufferedReader bufferedReader = new BufferedReader(
		    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
	    providedData = bufferedReader.readLine();
	    bufferedReader.close();
	    return providedData;
	} catch (MalformedURLException e) {
	    throw new DataNotFoundException("Data not found by " + CurrencyRateProviderNBP.class.getName()
		    + " on date " + localDate.toString(), e); 
	} catch (IOException e) {
	    throw new DataNotFoundException("Data not found by " + CurrencyRateProviderNBP.class.getName()
		    + " on date " + localDate.toString(), e);
	}
    }

}
