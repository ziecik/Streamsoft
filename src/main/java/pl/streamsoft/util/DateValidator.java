package pl.streamsoft.util;

import java.time.LocalDate;

import pl.streamsoft.exception.FutureDateException;

public class DateValidator {
	public static boolean isDateValid(LocalDate localDate) throws FutureDateException {
		return localDate.isBefore(LocalDate.now());
	}
}
