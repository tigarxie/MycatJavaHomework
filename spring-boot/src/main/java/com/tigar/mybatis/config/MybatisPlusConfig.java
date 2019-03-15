package com.tigar.mybatis.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MP配置
 *
 * @author tigar
 * @date 2018/12/12.
 */
@Configuration
@MapperScan(basePackages = "com.tigar.mybatis", sqlSessionTemplateRef = "tigarSqlSessionTemplate")
public class MybatisPlusConfig {

    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor sqlMonitorInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        return performanceInterceptor;
    }


    @Autowired
    @Qualifier("tigarDataSource")
    private DataSource dataSource;

    @Bean
    public MybatisSqlSessionFactoryBean tigarSqlSessionFactory() throws IOException {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // Mybatis配置
        MybatisConfiguration mcg = new MybatisConfiguration();
        mcg.setMapUnderscoreToCamelCase(false);
        bean.setConfiguration(mcg);

        // 全局配置
        GlobalConfiguration gc = new GlobalConfiguration();
        gc.setCapitalMode(false);
        gc.setDbColumnUnderline(false);
        gc.setIdType(0);
        gc.setFieldStrategy(2);
        gc.setSqlParserCache(true);
        bean.setGlobalConfig(gc);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
        bean.setTypeAliasesPackage("com.tigar.mybatis.po");

        // 附加插件
        List<Interceptor> interceptors = new ArrayList<>();

        // 分页插件
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType(DBType.SQLSERVER.getDb());
        interceptors.add(page);

//        // 性能监控插件
//        if (sqlMonitor != null) {
//            interceptors.add(sqlMonitor);
//        }

        bean.setPlugins(interceptors.toArray(new Interceptor[interceptors.size()]));
        return bean;
    }


    @Bean
    public SqlSessionTemplate tigarSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(tigarSqlSessionFactory().getObject());
        return template;
    }
}