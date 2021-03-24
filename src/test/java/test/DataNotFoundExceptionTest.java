package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import pl.streamsoft.exception.DataNotFoundException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.service.CurrencyRateProviderNBP;
import pl.streamsoft.util.StringDataProvider;

public class DataNotFoundExceptionTest {

    @Mock
    CurrencyRateProviderNBP currencyRateProviderNBP = Mockito.mock(CurrencyRateProviderNBP.class);

    @Test
    void should_throwDataNotFoundException_when_dataNotFound() {
//	given:
	setMissingData();

	Throwable thrown = catchThrowable(() -> StringDataProvider.getData(CurrencyCode.AUD,
		LocalDate.parse("2020-12-05"), currencyRateProviderNBP));

//	then:
	assertThat(thrown).isInstanceOf(DataNotFoundException.class);
	assertThat(thrown).hasMessage(
		"Choosen CurrencyRateProvider is not available right now. 5 attempts were made. Last on date: 2020-12-01");

    }

    private void setMissingData() {
	Mockito.when(currencyRateProviderNBP.getCurrencyRateData(CurrencyCode.AUD, LocalDate.parse("2020-12-05")))
		.thenThrow(DataNotFoundException.class);
	Mockito.when(currencyRateProviderNBP.getCurrencyRateData(CurrencyCode.AUD, LocalDate.parse("2020-12-04")))
		.thenThrow(DataNotFoundException.class);
	Mockito.when(currencyRateProviderNBP.getCurrencyRateData(CurrencyCode.AUD, LocalDate.parse("2020-12-03")))
		.thenThrow(DataNotFoundException.class);
	Mockito.when(currencyRateProviderNBP.getCurrencyRateData(CurrencyCode.AUD, LocalDate.parse("2020-12-02")))
		.thenThrow(DataNotFoundException.class);
	Mockito.when(currencyRateProviderNBP.getCurrencyRateData(CurrencyCode.AUD, LocalDate.parse("2020-12-01")))
		.thenThrow(DataNotFoundException.class);
    }
}
