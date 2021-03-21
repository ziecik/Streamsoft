package pl.streamsoft.exception;

public class DataNotFoundException extends RuntimeException {

	public DataNotFoundException(Throwable cause) {
		super("Choosen CurrencyRateService is not available right now", cause);
	}

}
