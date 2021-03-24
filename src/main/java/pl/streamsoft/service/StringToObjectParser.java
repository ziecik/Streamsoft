package pl.streamsoft.service;

import pl.streamsoft.model.CurrencyRate;

public interface StringToObjectParser {
    public CurrencyRate convertToCurrencyRate(String data);
}
