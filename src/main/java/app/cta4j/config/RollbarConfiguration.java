package app.cta4j.config;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.spring.webmvc.RollbarSpringConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Objects;

@Configuration
@EnableWebMvc
@ComponentScan({"com.rollbar.spring", "app.cta4j"})
public class RollbarConfiguration {
    @Bean
    public Rollbar rollbar(@Value("${rollbar.access-token}") String accessToken,
                           @Value("${rollbar.environment}") String environment) {
        Objects.requireNonNull(accessToken);

        Objects.requireNonNull(environment);

        Config config = RollbarSpringConfigBuilder.withAccessToken(accessToken)
                                                  .environment(environment)
                                                  .build();

        return new Rollbar(config);
    }
}
