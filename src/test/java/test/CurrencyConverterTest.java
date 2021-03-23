package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.exception.FutureDateException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.service.CurrencyRateNBPService;
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

	@Mock
	CurrencyRateNBPService mockedCurrencyRateService = Mockito.mock(CurrencyRateNBPService.class);

	@Test
	public void getRateDataDeliverNullNotFoundExceptionTest() throws IOException {

//		given:
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-05")))
				.thenReturn(null);
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-04")))
				.thenReturn(null);
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-03")))
				.thenReturn(null);
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-02")))
				.thenReturn(null);
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-01")))
				.thenReturn(null);
//		when:
		Throwable thrown = catchThrowable(() -> new CurrencyConverter(mockedCurrencyRateService)
				.convertToPLN(new BigDecimal("367.58"), CurrencyCode.EUR, LocalDate.parse("2020-12-05")));

//		then:
		assertThat(thrown).isInstanceOf(DataNotFoundException.class);
		assertThat(thrown).hasMessage(
				"Choosen CurrencyRateService does not provide correct data right now. [5 attempts were made]");

	}

/////
	@Test
	public void getRateDataNotFoundExceptionTest() throws IOException {
//		given:
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-05")))
				.thenThrow(new IOException());
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-04")))
				.thenThrow(new IOException());
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-03")))
				.thenThrow(new IOException());
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-02")))
				.thenThrow(new IOException());
		Mockito.when(mockedCurrencyRateService.getCurrencyRate(CurrencyCode.EUR, LocalDate.parse("2020-12-01")))
				.thenThrow(new IOException());
//		when:
		Throwable thrown = catchThrowable(() -> new CurrencyConverter(mockedCurrencyRateService)
				.convertToPLN(new BigDecimal("367.58"), CurrencyCode.EUR, LocalDate.parse("2020-12-05")));

//		then:
		assertThat(thrown).isInstanceOf(DataNotFoundException.class);
		assertThat(thrown).hasMessage("Choosen CurrencyRateService is not available right now. [5 attempts were made]");
		assertThat(thrown).hasCause(new IOException());
	}

	@Test
	public void convertToPLNOnSundayTest() {

//		given:
		CurrencyConverter currencyConverter = new CurrencyConverter();
		BigDecimal valueToConvert = new BigDecimal("1500.99");
		CurrencyCode euro = CurrencyCode.EUR;
		LocalDate sun = LocalDate.parse("2021-03-21");
		LocalDate fri = LocalDate.parse("2021-03-19");

//		when:
		BigDecimal valueInPLNOnSunday = currencyConverter.convertToPLN(valueToConvert, euro, sun);
		BigDecimal valueInPLNOnFriday = currencyConverter.convertToPLN(valueToConvert, euro, fri);

//		then:
		AssertJUnit.assertEquals(valueInPLNOnSunday, valueInPLNOnFriday);
	}

	@Test
	public void convertToPLNOnSaturdayTest() {

//		given:
		CurrencyConverter currencyConverter = new CurrencyConverter();
		BigDecimal valueToConvert = new BigDecimal("1500.99");
		CurrencyCode euro = CurrencyCode.EUR;
		LocalDate sat = LocalDate.parse("2021-03-20");
		LocalDate fri = LocalDate.parse("2021-03-19");

//		when:
		BigDecimal valueInPLNOnSaturady = currencyConverter.convertToPLN(valueToConvert, euro, sat);
		BigDecimal valueInPLNOnFriday = currencyConverter.convertToPLN(valueToConvert, euro, fri);

//		then:
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
