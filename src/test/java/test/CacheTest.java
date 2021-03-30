package test;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CurrencyConverter;
import pl.streamsoft.util.CacheMap;

public class CacheTest {

    @Test
    void should_addToCache_when_cachesIsEmpty() {	//	Observer needed
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	CurrencyCode czk = CurrencyCode.CZK;
	LocalDate date = LocalDate.of(2021, 3, 23);	
	BigDecimal valueToConvert = new BigDecimal("2000");
	
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, czk, date);
	Assertions.assertThat(CacheMap.cacheMap).isEmpty();	
	
	// when:
	
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	
	// then:
	String key = czk + date.toString();

	
	Assertions.assertThat(CacheMap.cacheMap.get(key)).isEqualTo(convertedToPLN.getCurrencyRateUsedToConvertion());
    }

    @Test
    void should_takeFromCache_when_cahceIsNotEmpty() {
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	
	CurrencyRate currencyRateAddedToCashe = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 23));
	CacheMap.cacheMap.put(currencyRateAddedToCashe.getId(), currencyRateAddedToCashe);
	
	// when:
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(new BigDecimal("100"), CurrencyCode.EUR, LocalDate.of(2021, 3, 23));
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	
	// then:
	
	Assertions.assertThat(convertedToPLN.getCurrencyRateUsedToConvertion()).isEqualTo(CacheMap.cacheMap.get("EUR2021-03-23"));
    }
    
    
}
