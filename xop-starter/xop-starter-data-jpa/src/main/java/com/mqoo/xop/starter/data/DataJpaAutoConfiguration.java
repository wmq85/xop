package com.mqoo.xop.starter.data;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mqoo.xop.starter.data.jdbc.JdbcOperation;
import com.mqoo.xop.starter.data.jdbc.JdbcOperationImpl;

/**
 * jpa 配置
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Configuration
public class DataJpaAutoConfiguration {
    /**
     * 注册jdbcTemplate
     * 
     * @param dataSource
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    /**
     * 注册 JdbcOperation
     * 
     * @param jdbcTemplate
     * @param environment
     * @return
     */
    @Bean
    @Primary
    public JdbcOperation jdbcOperation(JdbcTemplate jdbcTemplate, Environment environment) {
        JdbcOperationImpl jdbcOperation = new JdbcOperationImpl();
        jdbcOperation.setEnvironment(environment);
        jdbcOperation.setJdbcTemplate(jdbcTemplate);
        return jdbcOperation;
    }
}
