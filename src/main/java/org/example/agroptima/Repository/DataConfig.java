package org.example.agroptima.Repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataConfig {
    private final String url = "jdbc:postgresql://localhost:5432/agroptima";
    private final String user = "postgres";
    private final String password = "harentsoa";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return dataSource;
    }
}
