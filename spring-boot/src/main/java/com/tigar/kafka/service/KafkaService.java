package com.tigar.kafka.service;

import com.tigar.common.models.UserDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

/**
 * @author tigar
 * @date 2019/1/21.
 */
@Component
public class KafkaService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, UserDTO> kafkaTemplate1;

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/53 * * * * ?")
    public void producer1() {
        String message = UUID.randomUUID().toString();
        ListenableFuture future = kafkaTemplate.send("app_log", message);
        future.addCallback(o -> System.out.println("send-消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));
    }

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/53 * * * * ?")
    public void producer2() {
        UserDTO user = new UserDTO();
        user.setName(UUID.randomUUID().toString());
        user.setAge(UUID.randomUUID().hashCode());

        kafkaTemplate1.send("test_log", user);
    }

    @KafkaListener(topics = {"app_log"}, groupId = "spring.tests")
    public void consumer1(ConsumerRecord record) {
        System.out.println("kafka app_log 接收消息为:" + record.value());
    }

    @KafkaListener(topics = {"test_log"}, groupId = "spring.tests")
    public void consumer2(ConsumerRecord record) {
        System.out.println("kafka test_log 接收消息为:" + record.value());
    }
}
