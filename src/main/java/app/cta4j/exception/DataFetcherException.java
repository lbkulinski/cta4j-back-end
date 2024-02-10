package app.cta4j.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Objects;

public class DataFetcherException extends RuntimeException implements GraphQLError {
    private final ErrorClassification errorType;

    public DataFetcherException(String message, ErrorClassification errorType) {
        super(message);

        Objects.requireNonNull(errorType);

        this.errorType = errorType;
    }

    public DataFetcherException(ErrorClassification errorType) {
        this("An error occurred when trying to fetch data from the CTA's API", errorType);
    }

    @Override
    public ErrorClassification getErrorType() {
        return this.errorType;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
