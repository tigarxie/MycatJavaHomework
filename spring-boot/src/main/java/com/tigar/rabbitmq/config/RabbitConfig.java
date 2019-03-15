package com.tigar.rabbitmq.config;

import com.tigar.rabbitmq.constant.MQConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tigar
 * @date 2019/1/21.
 */
@Configuration
public class RabbitConfig {

    /**
     * 没定义他是强命名，发送的实体跟接受实体必须同一个命名空间
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 交换机声明
    @Bean
    public DirectExchange supplyExchange() {
        return new DirectExchange(MQConst.EXCHANGE);
    }


    // 队列声明
    @Bean
    public Queue replenishmentQueue() {
        return new Queue(MQConst.QUEUE_1);
    }

    // 绑定队列
    @Bean
    public Binding replenishmentBindingExchange(Queue replenishmentQueue, DirectExchange supplyExchange) {
        return BindingBuilder.bind(replenishmentQueue).to(supplyExchange).with(MQConst.ROUTEKEY_1);
    }


}