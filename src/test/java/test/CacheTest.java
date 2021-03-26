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
import pl.streamsoft.util.LRUCacheMap;

public class CacheTest {

    @Test
    void should_addDataToCache_when_dataWasUsedAndWasNotInCache() {
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	CurrencyCode czk = CurrencyCode.CZK;
	LocalDate date = LocalDate.of(2021, 3, 23);
	
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(new BigDecimal("2000"), czk, date);
	
	// when:
	
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	// then:
	String key = czk + date.toString();

	
	Assertions.assertThat(CurrencyConverter.cacheMap.get(key)).isEqualTo(convertedToPLN.getCurrencyRateUsedToConvertion());
    }

    @Test
    void should_takeDataFromCache_when_dataIsStoredInCache() {
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	LRUCacheMap cacheMap = CurrencyConverter.cacheMap;
	CurrencyRate currencyRateAddedToCashe = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 23));
	cacheMap.put(currencyRateAddedToCashe.getId(), currencyRateAddedToCashe);
	// when:
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(new BigDecimal("100"), CurrencyCode.EUR, LocalDate.of(2021, 3, 23));
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	// then:
	Assertions.assertThat(convertedToPLN.getCurrencyRateUsedToConvertion()).isEqualTo(currencyRateAddedToCashe);
    }
    
    
}
