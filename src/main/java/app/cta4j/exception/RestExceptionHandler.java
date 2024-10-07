package app.cta4j.exception;

import com.rollbar.notifier.Rollbar;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    }

    @Autowired
    public RestExceptionHandler(Rollbar rollbar) {
        this.rollbar = Objects.requireNonNull(rollbar);
    }

    @ExceptionHandler(value = {DataAccessException.class, RestClientException.class, IllegalStateException.class})
    private ResponseEntity<?> handleInternalServerError(Exception e) {
        Objects.requireNonNull(e);

        this.rollbar.error(e);

        String message = e.getMessage();

        RestExceptionHandler.LOGGER.error(message, e);

        return ResponseEntity.internalServerError()
                             .build();
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    private ResponseEntity<?> handleNotFound(Exception e) {
        Objects.requireNonNull(e);

        this.rollbar.error(e);

        String message = e.getMessage();

        RestExceptionHandler.LOGGER.info(message, e);

        return ResponseEntity.notFound()
                             .build();
    }
}
