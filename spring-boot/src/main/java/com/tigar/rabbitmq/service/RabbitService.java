package com.tigar.rabbitmq.service;

import com.tigar.rabbitmq.constant.MQConst;
import com.tigar.rabbitmq.models.UserDTO;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author tigar
 * @date 2019/1/21.
 */
@Component
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时任务1
     */
    @Scheduled(cron = "0/62 * * * * ?")
    public void producer1() {
        try {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(MQConst.EXCHANGE, MQConst.ROUTEKEY_1, UUID.randomUUID().toString(), correlationData);
            boolean rs = correlationData.getFuture().get(5, TimeUnit.SECONDS).isAck();
            assert rs;          // 需配置 spring.rabbitmq.publisher-confirms: true
        } catch (Exception e) {

        }
    }

    /**
     * 定时任务2
     */
    @Scheduled(cron = "0/65 * * * * ?")
    public void producer2() {
        try {
            UserDTO user = new UserDTO();
            user.setName(UUID.randomUUID().toString());
            user.setClassName(user.getName());
            user.setAge(3);

            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(MQConst.EXCHANGE, MQConst.ROUTEKEY_2, user, correlationData);
            System.out.println("发信息，唯一标识：" + correlationData);

        } catch (Exception e) {

        }
    }

    /**
     * 消费者1
     *
     * @param msg
     */
    @RabbitListener(queues = MQConst.QUEUE_1, concurrency = "5")    // 5个消费者，相当于5线程
    public void consumer1(String msg) {
        System.out.println(Thread.currentThread().getName() + " rabbtimq 接收到来自hello.queue2队列的消息：" + msg);
    }

    /**
     * 消费者2
     *
     * @param user
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MQConst.EXCHANGE),
            value = @Queue(value = MQConst.QUEUE_2),
            key = MQConst.ROUTEKEY_2))
    public void consumer2(com.tigar.common.models.UserDTO user) {
        System.out.println(Thread.currentThread().getName() + " rabbtimq 接收到来自hello.queue2队列的消息：" + user);
    }
}
