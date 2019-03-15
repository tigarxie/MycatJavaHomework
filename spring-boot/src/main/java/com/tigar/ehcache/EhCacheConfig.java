package com.tigar.ehcache;


import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * @author tigar
 * @date 2019/1/15.
 */
@org.springframework.context.annotation.Configuration
public class EhCacheConfig {

    /**
     * 通用缓存名称
     */
    private String cacheName = "SPRING_EHCACHE";

    @Bean
    public Configuration ehcacheConfig() {
        Configuration config = new Configuration();

        // 以下也可以通过配置来做
        // spring.cache.ehcache.config=classpath:resources/ehcache-config.xml

        // 默认
        CacheConfiguration defaultConfig = new CacheConfiguration();
        defaultConfig.setMaxEntriesInCache(500);
        defaultConfig.setEternal(false);
        defaultConfig.timeToIdleSeconds(300);
        defaultConfig.timeToLiveSeconds(1200);
        config.setDefaultCacheConfiguration(defaultConfig);

        // 特定的
        CacheConfiguration springTestConfig = new CacheConfiguration();
        springTestConfig.setName(cacheName);
        springTestConfig.setMaxEntriesInCache(500);
        springTestConfig.setEternal(false);
        springTestConfig.timeToIdleSeconds(300);
        springTestConfig.timeToLiveSeconds(1200);
        config.addCache(springTestConfig);
        return config;
    }

}
