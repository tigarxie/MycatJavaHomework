package com.tigar.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tigar
 * @date 2019/3/12.
 */
@Configuration
public class RibbonConfig {
    /**
     * 随机路由
     * @return
     */
    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}
