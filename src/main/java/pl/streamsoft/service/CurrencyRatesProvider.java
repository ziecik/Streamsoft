package pl.streamsoft.service;

public enum CurrencyRatesProvider {
	NBP(new CurrencyRateNBPService()),
	CSV_FILE(new CurrencyRateCSVFIleService()), 
	UNV(new CIrrencyRateUNVService());

	private final CurrencyRateService currencyRateService;

	CurrencyRatesProvider(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public CurrencyRateService getCurrencyRateService() {
		return currencyRateService;
	}
}
