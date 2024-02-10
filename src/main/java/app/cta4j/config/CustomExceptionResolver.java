package app.cta4j.config;

import app.cta4j.exception.DataFetcherException;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.ResultPath;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.Objects;

@Component
public final class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable throwable,
        @NonNull DataFetchingEnvironment environment) {
        Objects.requireNonNull(throwable);

        Objects.requireNonNull(environment);

        if (throwable instanceof DataFetcherException exception) {
            ErrorClassification errorType = exception.getErrorType();

            String message = throwable.getMessage();

            ResultPath path = environment.getExecutionStepInfo()
                                         .getPath();

            SourceLocation location = environment.getField()
                                                 .getSourceLocation();

            return GraphqlErrorBuilder.newError()
                                      .errorType(errorType)
                                      .message(message)
                                      .path(path)
                                      .location(location)
                                      .build();
        } else if (throwable instanceof BindException) {
            String message = "The specified station ID must be positive integer";

            ResultPath path = environment.getExecutionStepInfo()
                                         .getPath();

            SourceLocation location = environment.getField()
                                                 .getSourceLocation();

            return GraphqlErrorBuilder.newError()
                                      .errorType(ErrorType.BAD_REQUEST)
                                      .message(message)
                                      .path(path)
                                      .location(location)
                                      .build();
        }

        return null;
    }
}
