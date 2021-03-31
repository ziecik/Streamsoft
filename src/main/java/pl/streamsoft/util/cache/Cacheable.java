package pl.streamsoft.util.cache;

import pl.streamsoft.model.CurrencyRateUpdater;
import pl.streamsoft.util.CurrencyRateSource;

public interface Cacheable extends CurrencyRateSource, CurrencyRateUpdater {

}
