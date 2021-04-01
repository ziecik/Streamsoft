package pl.streamsoft.util.cache.v3;

import java.time.LocalDateTime;

public interface CacheValue<V> {
    V getValue();
    LocalDateTime getCreatedAt();
}
