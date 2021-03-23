package pl.streamsoft.service;

import java.io.IOException;
import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;

public interface CurrencyRateProvider {
    public String getCurrencyRateData(CurrencyCode currencyCode, LocalDate localDate) throws IOException;
}
