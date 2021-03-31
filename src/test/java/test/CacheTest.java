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
import pl.streamsoft.util.cache.LRUSource;
import pl.streamsoft.util.cache.old.CacheMap;

public class CacheTest {

    @Test
    void should_addToCache_when_cachesIsEmpty() {	
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	CurrencyCode czk = CurrencyCode.CZK;
	LocalDate date = LocalDate.of(2021, 3, 23);	
	BigDecimal valueToConvert = new BigDecimal("2000");
	
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, czk, date);
	Assertions.assertThat(LRUSource.lruCashMap.getMap().isEmpty());	
	
	// when:
	
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	
	// then:
	String key = czk + date.toString();
	Assertions.assertThat(LRUSource.lruCashMap.get(key)).isEqualTo(convertedToPLN.getCurrencyRateUsedToConvertion());
    }

    @Test
    void should_takeFromCache_when_cahceIsNotEmpty() {
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	
	CurrencyRate currencyRateAddedToCashe = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 23));
	LRUSource.lruCashMap.put(currencyRateAddedToCashe.getId(), currencyRateAddedToCashe);
	
	// when:
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(new BigDecimal("100"), CurrencyCode.EUR, LocalDate.of(2021, 3, 23));
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	
	// then:
	
	Assertions.assertThat(convertedToPLN.getCurrencyRateUsedToConvertion()).isEqualTo(LRUSource.lruCashMap.get("EUR2021-03-23"));
    }
    
    
    @Test
    void should_removeOldestValeu_when_addToFullCache() {	
	// given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	CurrencyCode czk = CurrencyCode.CZK;
	LocalDate sunday = LocalDate.of(2021, 3, 7);	
	BigDecimal valueToConvert = new BigDecimal("2000");
	
	makingCacheFull();
		
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, czk, sunday);
		
	// when:
	
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);
	
	// then:
	LocalDate friday = sunday.minusDays(2);
	String key = czk + friday.toString();
	Assertions.assertThat(LRUSource.lruCashMap.get(key)).isEqualTo(convertedToPLN.getCurrencyRateUsedToConvertion());
//	Assertions.assertThat(LRUSource.lruCashMap.get("CZK2021-03-23")).isEqualTo(null);
	System.out.println(LRUSource.lruCashMap.getMap().size());
    }

    private void makingCacheFull() {
	CurrencyRate currency1 = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 23));
	CurrencyRate currency2 = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 24));
	CurrencyRate currency3 = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 25));
	CurrencyRate currency4 = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 26));
	CurrencyRate currency5 = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 27));
	CurrencyRate currency6 = new CurrencyRate("euro", CurrencyCode.EUR, new BigDecimal("4.4444"), LocalDate.of(2021, 3, 28));
	
	LRUSource.lruCashMap.put(currency1.getId(), currency1);
	LRUSource.lruCashMap.put(currency2.getId(), currency2);
	LRUSource.lruCashMap.put(currency3.getId(), currency3);
	LRUSource.lruCashMap.put(currency4.getId(), currency4);
	LRUSource.lruCashMap.put(currency5.getId(), currency5);
//	LRUSource.lruCashMap.put(currency6.getId(), currency6);
    }
    
}
