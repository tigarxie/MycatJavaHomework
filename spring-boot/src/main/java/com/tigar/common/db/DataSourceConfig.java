package com.tigar.common.db;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 数据源配置
 * 文件配置：https://blog.csdn.net/weinichendian/article/details/78503469
 *
 * @author tigar
 * @date 2018/12/12.
 */
@Component
public class DataSourceConfig {

    @Bean
    public StatFilter statFilter(){
        StatFilter filter = new StatFilter();
        filter.setLogSlowSql(true);
        filter.setSlowSqlMillis(10000);
        return filter;
    }

    @Bean
    public LogFilter logFilter(){
        LogFilter filter = new Log4j2Filter();
        // 是否显示SQL语句
        filter.setStatementExecutableSqlLogEnable(true);
        // 开启错误日志
        filter.setStatementLogErrorEnabled(true);
        return filter;
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.tigar")
    public DataSource tigarDataSource(StatFilter statFilter, LogFilter logFilter) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Arrays.asList(statFilter, logFilter));
        return druidDataSource;
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.lily")
    public DataSource lilyDataSource(StatFilter statFilter, LogFilter logFilter) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Arrays.asList(statFilter, logFilter));
        return druidDataSource;
    }
}
