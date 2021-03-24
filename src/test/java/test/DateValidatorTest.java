package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.testng.annotations.Test;

import pl.streamsoft.exception.FutureDateException;
import pl.streamsoft.util.DateValidator;

public class DateValidatorTest {

    @Test
    public void should_throwFutudeDataException_when_dateIsFromFuture() {

//	given: 
	LocalDate localDate = LocalDate.now().plusDays(1);

//	when: 
	Throwable thrown = catchThrowable(() -> DateValidator.validateDate(localDate));

//	then: 
	assertThat(thrown).isInstanceOf(FutureDateException.class);
	assertThat(thrown).hasMessage("Currency rate on this day is not announced yet");
    }
}