package pl.streamsoft.service;

import pl.streamsoft.model.CurrencyRate;

public interface StringToObjectConverter {
    public CurrencyRate convertToCurrencyRate(String data);
}
