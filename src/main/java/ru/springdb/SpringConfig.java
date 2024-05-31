package ru.springdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Spring configuration class for setting up application context and beans.
 */
@Configuration
@ComponentScan("ru.springdb")
@PropertySource("classpath:application.properties")
public class SpringConfig {
    @Autowired
    private Environment env;

    /**
     * Configures and returns a DataSource bean.
     *
     * @return the configured DataSource
     */
    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("dataSource.driverClassName")));
        dataSource.setUrl(env.getProperty("dataSource.url"));
        dataSource.setUsername(env.getProperty("dataSource.username"));
        dataSource.setPassword(env.getProperty("dataSource.password"));
        return dataSource;
    }
}
