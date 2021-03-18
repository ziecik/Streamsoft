package pl.streamsoft.exception;

public class FutureDateException extends RuntimeException {

	public FutureDateException() {
		super("Currency rate on this day is not announced yet");
	}
	
}
