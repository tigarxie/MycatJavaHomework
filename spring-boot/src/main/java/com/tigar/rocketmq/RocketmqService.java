package com.tigar.rocketmq;

import com.tigar.common.models.UserDTO;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 使用：https://github.com/apache/rocketmq-spring
 * https://github.com/apache/-externals
 * @author tigar
 * @date 2019/3/2.
 */
@Component
public class RocketmqService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final String topic1 = "rocketmq_test1";

    private final String topic2 = "rocketmq_test2";

    /**
     * 同步发送
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void producer1() {
        try {
            String msg = UUID.randomUUID().toString();
            SendResult sr = rocketMQTemplate.syncSend(topic1,
                    MessageBuilder.withPayload(msg));
            if (sr.getSendStatus() == SendStatus.SEND_OK) {
                System.out.println(String.format("同步发送成功：%s", msg));
            } else {
                System.out.println(String.format("同步发送失败：%s", msg));
            }
        } catch (Exception e) {
            System.out.println(String.format("同步发送异常：%s", e.getMessage()));
        }
    }

    /**
     * 定时任务2
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void producer2() {
        String msg = UUID.randomUUID().toString();

        UserDTO user = new UserDTO();
        user.setName(msg);
        user.setClassName(user.getName());
        user.setAge(3);

        rocketMQTemplate.asyncSend(topic2,
                MessageBuilder.withPayload(UUID.randomUUID().toString()),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                            System.out.println(String.format("异步发送成功：%s", msg));
                        } else {
                            System.out.println(String.format("异步发送失败：%s", msg));
                        }
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println(String.format("异步发送异常：%s", throwable.getMessage()));
                    }
                });
    }


    /**
     * 消费者1
     */
    @Service
    @RocketMQMessageListener(topic = topic1, consumerGroup = "rocketmq_consumers1")
    public class Consumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            System.out.println(String.format("rocketmq 消费者组1收到消息：%s", s));
        }
    }

    /**
     * 消费者2
     */
    @Service
    @RocketMQMessageListener(topic = topic2, consumerGroup = "rocketmq_consumers2")
    public class Consumer2 implements RocketMQListener<UserDTO> {
        @Override
        public void onMessage(UserDTO s) {
            System.out.println(String.format("rocketmq 消费者组2收到消息：%s", s.toString()));
        }
    }
}

