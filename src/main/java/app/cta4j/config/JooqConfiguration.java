package app.cta4j.config;

import app.cta4j.service.SecretService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class JooqConfiguration {
    private final SecretService secretService;

    @Autowired
    public JooqConfiguration(SecretService secretService) {
        this.secretService = Objects.requireNonNull(secretService);
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        String url = this.secretService.getSecret("JDBC_URL");

        if (url == null) {
            throw new IllegalStateException("JDBC_URL is required");
        }

        hikariConfig.setJdbcUrl(url);

        String username = this.secretService.getSecret("JDBC_USERNAME");

        if (username == null) {
            throw new IllegalStateException("JDBC_USERNAME is required");
        }

        hikariConfig.setUsername(username);

        String password = this.secretService.getSecret("JDBC_PASSWORD");

        if (password == null) {
            throw new IllegalStateException("JDBC_PASSWORD is required");
        }

        hikariConfig.setPassword(password);

        String driverClassName = "org.postgresql.Driver";

        hikariConfig.setDriverClassName(driverClassName);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public DefaultConfiguration defaultConfiguration(DataSource dataSource) {
        DefaultConfiguration configuration = new DefaultConfiguration();

        configuration.setDataSource(dataSource);

        configuration.setSQLDialect(SQLDialect.POSTGRES);

        return configuration;
    }
}
