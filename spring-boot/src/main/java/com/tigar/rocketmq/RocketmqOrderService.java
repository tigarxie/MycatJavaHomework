package com.tigar.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统做法：https://www.cnblogs.com/Jtianlin/p/8436024.html
 * @author tigar
 * @date 2019/3/3.
 */
@Component
public class RocketmqOrderService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final String topic1 = "rocketmq_order_test1";

    ExecutorService pool = Executors.newFixedThreadPool(3);

    /**
     * 顺序发送
     */
    @Scheduled(cron = "0/8 * * * * ?")
    public void producer1() {
        for (int c = 0; c < 3; c++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    String msg = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
                    for (int i = 0; i < 10; i++) {
                        rocketMQTemplate.syncSendOrderly(topic1,
                                String.format("rocketmq %s 顺序消息 %d", msg, i), msg);
                    }
                }
            });
        }
    }

    /**
     * 消费者1
     */
    @Service
    @RocketMQMessageListener(topic = topic1, consumerGroup = topic1, consumeThreadMax = 3)
    public class Consumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            System.out.println(String.format("rocketmq 线程号：%d 顺序消费者组1收到消息：%s",
                    Thread.currentThread().getId(), s));
        }
    }
}
