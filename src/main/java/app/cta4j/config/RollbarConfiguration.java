package app.cta4j.config;

import app.cta4j.service.SecretService;
import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.spring.webmvc.RollbarSpringConfigBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RollbarConfiguration {
    private final SecretService secretService;

    @Autowired
    public RollbarConfiguration(SecretService secretService) {
        this.secretService = Objects.requireNonNull(secretService);
    }

    @Bean
    public Rollbar rollbar(@Value("${rollbar.environment}") String environment) {
        Objects.requireNonNull(environment);

        String key = "ROLLBAR_ACCESS_TOKEN";

        String accessToken = this.secretService.getSecret(key);

        Config config = RollbarSpringConfigBuilder.withAccessToken(accessToken)
                                                  .environment(environment)
                                                  .build();

        return new Rollbar(config);
    }
}
