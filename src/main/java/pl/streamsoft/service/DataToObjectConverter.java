package pl.streamsoft.service;

import pl.streamsoft.model.CurrencyRate;

public interface DataToObjectConverter {
    public CurrencyRate convertToCurrencyRate(String data);
}
