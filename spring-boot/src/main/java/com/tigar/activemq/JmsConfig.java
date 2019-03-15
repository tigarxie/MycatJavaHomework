package com.tigar.activemq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;

/**
 * 多数据源：https://www.jianshu.com/p/a924c30554ca
 *
 * @author tigar
 * @date 2019/2/21.
 */
@Configuration
public class JmsConfig {

//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListener(ConnectionFactory connectionFactory)
//    {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(true);
//        factory.setMessageConverter(jackson2MessageConverter());
//        factory.setErrorHandler(new ErrorHandler(){
//            @Override
//            public void handleError(Throwable throwable) {
//
//            }
//        });
//        return factory;
//    }

    /**
     * JSON 序列化
     *
     * @return
     */
    @Bean
    public MappingJackson2MessageConverter jackson2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.BYTES);
        converter.setTypeIdPropertyName("DocumentType");
        return converter;
    }
}
