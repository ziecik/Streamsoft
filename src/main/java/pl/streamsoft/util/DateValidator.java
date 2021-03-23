package pl.streamsoft.util;

import java.time.LocalDate;

import pl.streamsoft.exception.FutureDateException;

public class DateValidator {
	
	public static void validateDate(LocalDate localDate) {
		if(localDate.isAfter(LocalDate.now()))
			throw new FutureDateException();
	}
}
