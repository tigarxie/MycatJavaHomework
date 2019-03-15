package com.tigar.jdbcTemplate.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author tigar
 * @date 2019/2/20.
 */
@Configuration
public class JdbcTemplateConfig {

    @Bean
    public JdbcTemplate tigarJdbcTemplate(
            @Qualifier("tigarDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate lilyJdbcTemplate(
            @Qualifier("lilyDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
