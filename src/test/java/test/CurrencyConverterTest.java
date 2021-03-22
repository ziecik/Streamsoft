package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.exception.FutureDateException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.service.CurrencyRateCSVFIleService;
import pl.streamsoft.service.CurrencyRateService;
import pl.streamsoft.service.ReturnNullService;
import pl.streamsoft.util.CurrencyConverter;
import pl.streamsoft.util.DateValidator;

public class CurrencyConverterTest {

	@Test
	public void dataValidatorDateExceptionTest() {
		LocalDate localDate;
		Throwable thrown;

//		given: 
		localDate = LocalDate.now().plusDays(1);

//		when: 
		thrown = catchThrowable(() -> DateValidator.validateDate(localDate));

//		then: 
		assertThat(thrown).isInstanceOf(FutureDateException.class);
		assertThat(thrown).hasMessage("Currency rate on this day is not announced yet");
	}

	@Test
	public void getRateDataDeliverNullNotFoundExceptionTest() {

//		given:
		CurrencyRateService currencyRateService = new ReturnNullService();
		// currencyRateService. getCurrencyRate(CurrencyCode newCurrencyCode,LocalDate
		// localDate) return null

//		when:
		Throwable thrown = catchThrowable(() -> new CurrencyConverter(currencyRateService)
				.convertToPLN(new BigDecimal("367.58"), CurrencyCode.EUR, LocalDate.parse("2020-12-04")));

//		then:
		assertThat(thrown).isInstanceOf(DataNotFoundException.class);
		assertThat(thrown).hasMessage(
				"Choosen CurrencyRateService does not provide correct data right now. [5 attempts were made]");

	}

	@Test
	public void getRateDataNotFoundExceptionTest() {

//		given:
		CurrencyRateService currencyRateService = new CurrencyRateCSVFIleService();
		// currencyRateService. getCurrencyRate(CurrencyCode newCurrencyCode,LocalDate
		// localDate) throws IOException

//		when:
		Throwable thrown = catchThrowable(() -> new CurrencyConverter(currencyRateService)
				.convertToPLN(new BigDecimal("367.58"), CurrencyCode.EUR, LocalDate.parse("2020-12-04")));

//		then:
		assertThat(thrown).isInstanceOf(DataNotFoundException.class);
		assertThat(thrown).hasMessage("Choosen CurrencyRateService is not available right now. [5 attempts were made]");
	}

	@Test
	public void convertToPLNOnWeekendTest() {

//		given:
		CurrencyConverter currencyConverter = new CurrencyConverter();
		BigDecimal valueToConvert = new BigDecimal("1500.99");
		CurrencyCode euro = CurrencyCode.EUR;
		LocalDate sun = LocalDate.parse("2021-03-21");
		LocalDate sat = LocalDate.parse("2021-03-20");
		LocalDate fri = LocalDate.parse("2021-03-19");

//		when:
		BigDecimal valueInPLNOnSunday = currencyConverter.convertToPLN(valueToConvert, euro, sun);
		BigDecimal valueInPLNOnSaturady = currencyConverter.convertToPLN(valueToConvert, euro, sat);
		BigDecimal valueInPLNOnFriday = currencyConverter.convertToPLN(valueToConvert, euro, fri);

//		then:
		AssertJUnit.assertEquals(valueInPLNOnSunday, valueInPLNOnFriday);
		AssertJUnit.assertEquals(valueInPLNOnSaturady, valueInPLNOnFriday);
	}

	@Test
	public void convertToPLNOnHolidayTest() {

//		given:
		CurrencyConverter currencyConverter = new CurrencyConverter();
		BigDecimal valueToConvert = new BigDecimal("1500.99");
		CurrencyCode forint = CurrencyCode.HUF;
		LocalDate epiphanyWednesday = LocalDate.parse("2021-01-06");
		LocalDate tuesday = LocalDate.parse("2021-01-05");

//		when:
		BigDecimal valueInPLNOnEpiphanyWednesday = currencyConverter.convertToPLN(valueToConvert, forint,
				epiphanyWednesday);
		BigDecimal valueInPLNOnTuesday = currencyConverter.convertToPLN(valueToConvert, forint, tuesday);

//		then:
		AssertJUnit.assertEquals(valueInPLNOnEpiphanyWednesday, valueInPLNOnTuesday);
	}

}
