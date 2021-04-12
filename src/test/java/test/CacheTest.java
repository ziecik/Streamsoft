package test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;
import pl.streamsoft.util.CurrencyConverter;
import pl.streamsoft.util.CurrencyRateSource;
import pl.streamsoft.util.ExternalCurrencyRateSource;
import pl.streamsoft.util.cache.v3.CurrencyRateCache;

public class CacheTest {

    @Test
    void should_addToCache_when_cachesIsEmpty() {
	// given:
	CurrencyRateCache currencyRateCache = new CurrencyRateCache();
	ExternalCurrencyRateSource externalCurrencyRateSource = new ExternalCurrencyRateSource();
	List<CurrencyRateSource> currencyRateSources = new ArrayList<>();
	currencyRateSources.add(currencyRateCache);
	currencyRateSources.add(externalCurrencyRateSource);

	CurrencyConverter currencyConverter = new CurrencyConverter(currencyRateSources);

	CurrencyCode czk = CurrencyCode.CZK;
	LocalDate date = LocalDate.of(2021, 3, 23);
	BigDecimal valueToConvert = new BigDecimal("2000");

	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, czk, date);
	Assertions.assertThat(currencyRateCache.isEmpty());

	// when:

	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);

	// then:
	String key = czk + date.toString();
	Assertions.assertThat(currencyRateCache.get(key).get())
		.isEqualTo(convertedToPLN.getCurrencyRateUsedToConvertion());
    }

    @Test
    void should_takeFromCache_when_dataInCache() {
	// given:
	CurrencyRateCache currencyRateCache = new CurrencyRateCache();
	ExternalCurrencyRateSource externalCurrencyRateSource = new ExternalCurrencyRateSource();
	List<CurrencyRateSource> currencyRateSources = new ArrayList<>();
	currencyRateSources.add(currencyRateCache);
	currencyRateSources.add(externalCurrencyRateSource);

	CurrencyConverter currencyConverter = new CurrencyConverter(currencyRateSources);

	Assertions.assertThat(currencyRateCache.isEmpty());

	CurrencyRate currencyRateAddedToCashe = new CurrencyRate(CurrencyCode.EUR, "euro", new BigDecimal("4.4444"),
		LocalDate.of(2021, 3, 23));
	currencyRateCache.put(currencyRateAddedToCashe.getId(), currencyRateAddedToCashe);

	// when:
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(new BigDecimal("100"), CurrencyCode.EUR,
		LocalDate.of(2021, 3, 23));
	ConvertedAmount convertedToPLN = currencyConverter.convertToPLN(amountDataToConvert);

	// then:

	Assertions.assertThat(convertedToPLN.getCurrencyRateUsedToConvertion())
		.isEqualTo(currencyRateCache.get("EUR2021-03-23").get());
    }

    @Test
    void should_removeValueFromCache_when_keyExpired() throws InterruptedException {
	// given:
	CurrencyRate currencyRate = new CurrencyRate( CurrencyCode.EUR, "euro", new BigDecimal("4.4444"),
		LocalDate.now());
	String id = currencyRate.getId();

	CurrencyRateCache currencyRateCache = new CurrencyRateCache(3000);

	// when:
	
	currencyRateCache.put(id, currencyRate);
	 
	Thread.sleep(3000);

	// then:
	Assertions.assertThat(currencyRateCache.get(id)).isEqualTo(Optional.empty());
    }

}
