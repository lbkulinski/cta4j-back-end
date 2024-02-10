package app.cta4j.serialization;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class BusStringToInstantConverter extends StdConverter<String, Instant> {
    @Override
    public Instant convert(String string) {
        Objects.requireNonNull(string);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm");

        ZoneId chicagoId = ZoneId.of("America/Chicago");

        return LocalDateTime.parse(string, formatter)
                            .atZone(chicagoId)
                            .toInstant();
    }
}
