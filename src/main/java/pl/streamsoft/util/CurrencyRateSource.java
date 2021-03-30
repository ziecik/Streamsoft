package pl.streamsoft.util;

import java.time.LocalDate;

import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.model.CurrencyRate;

public interface CurrencyRateSource {

    CurrencyRate getCurrencyRate(CurrencyCode currencyCode, LocalDate dateOfConversion);
}
