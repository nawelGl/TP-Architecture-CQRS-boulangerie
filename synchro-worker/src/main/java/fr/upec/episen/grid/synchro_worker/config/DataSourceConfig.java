package fr.upec.episen.grid.synchro_worker.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSourceWrite")
    @ConfigurationProperties(prefix = "spring.datasources.write")
    public DataSource dataSourceWrite() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dataSourceRead")
    @ConfigurationProperties(prefix = "spring.datasources.read")
    public DataSource dataSourceRead() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcWrite")
    public JdbcTemplate jdbcTemplateWrite(@Qualifier("dataSourceWrite") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "jdbcRead")
    public JdbcTemplate jdbcTemplateRead(@Qualifier("dataSourceRead") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}