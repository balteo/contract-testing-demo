package org.example.contracttestingdemo.configuration;

import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfiguration extends AbstractR2dbcConfiguration {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        Map<H2ConnectionOption, String> properties = new HashMap<>();
        properties.put(H2ConnectionOption.DB_CLOSE_ON_EXIT, "false");
        properties.put(H2ConnectionOption.DB_CLOSE_DELAY, "-1");

        return H2ConnectionFactory
            .inMemory("testdb", "sa", "", properties);
    }
}
