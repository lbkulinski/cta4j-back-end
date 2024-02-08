package app.cta4j.serialization;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;
import java.util.Objects;

public final class InstantToStringConverter extends StdConverter<Instant, String> {
    @Override
    public String convert(Instant instant) {
        Objects.requireNonNull(instant);

        return instant.toString();
    }
}
