package pl.streamsoft.exception;

public class CurrencyRateServiceUnavailableException extends RuntimeException {

	public CurrencyRateServiceUnavailableException() {
		super("Choosen CurrencyRateService is not available right now");
	}

}
