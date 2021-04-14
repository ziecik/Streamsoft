package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.util.CurrencyConverter;
import pl.streamsoft.util.CurrencyRateSource;
import pl.streamsoft.util.ExternalCurrencyRateSource;

public class RollbackDateTest {

	@Test
	void should_rollbackDateToFriday_when_dataNofFoundOnSaturday() {

//		given:
		List<CurrencyRateSource> currencyRateSources = new ArrayList<>();
		currencyRateSources.add(new ExternalCurrencyRateSource());
		CurrencyConverter currencyConverter = new CurrencyConverter(currencyRateSources);
		BigDecimal valueToConvert = new BigDecimal("1500.99");
		CurrencyCode euro = CurrencyCode.EUR;
		LocalDate sat = LocalDate.parse("2021-03-20");

		AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, euro, sat);

//		when:
		ConvertedAmount convertedAmount = currencyConverter.convertToPLN(amountDataToConvert);

//		then:
		LocalDate fri = LocalDate.parse("2021-03-19");
		LocalDate dateOfAnnouncedRate = convertedAmount.getCurrencyRateUsedToConvertion().getDateOfAnnouncedRate();
		assertThat(dateOfAnnouncedRate).isEqualTo(fri);
	}

	@Test
	void should_rollbackDateToPreviousDateWithData_when_dataNofFoundOnHoliday() {

//		given:
		List<CurrencyRateSource> currencyRateSources = new ArrayList<>();
		currencyRateSources.add(new ExternalCurrencyRateSource());
		CurrencyConverter currencyConverter = new CurrencyConverter(currencyRateSources);
		BigDecimal valueToConvert = new BigDecimal("1500.99");
		CurrencyCode euro = CurrencyCode.EUR;
		LocalDate hiliday = LocalDate.parse("2021-01-06");

		AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, euro, hiliday);

//		when:
		ConvertedAmount convertedAmount = currencyConverter.convertToPLN(amountDataToConvert);

//		then:
		LocalDate dayBeforeHolidayWithData = LocalDate.parse("2021-01-05");
		LocalDate dateOfAnnouncedRate = convertedAmount.getCurrencyRateUsedToConvertion().getDateOfAnnouncedRate();

		assertThat(dateOfAnnouncedRate).isEqualTo(dayBeforeHolidayWithData);
	}
}
