package pl.streamsoft.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
	super("Choosen CurrencyRateService is not available right now");
}
	public DataNotFoundException(Throwable cause) {
		super("Choosen CurrencyRateService is not available right now", cause);
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
