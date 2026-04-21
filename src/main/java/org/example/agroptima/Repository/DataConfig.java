package org.example.agroptima.Repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        dataSource.setDriverClassName("org.postgresql.Driver");

        dataSource.setUrl(url != null ? url : "jdbc:postgresql://localhost:5432/agroptima");
        dataSource.setUsername(user != null ? user : "user123");
        dataSource.setPassword(password != null ? password : "123456");

        return dataSource;
    }
}