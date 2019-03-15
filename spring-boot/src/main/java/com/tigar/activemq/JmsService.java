package com.tigar.activemq;

import com.tigar.common.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * activeMQ原生调用：https://www.cnblogs.com/jaycekon/p/6225058.html
 * spring boot 整合：https://www.cnblogs.com/Alex-zqzy/p/9558857.html
 * JMS设计思路：https://www.cnblogs.com/cyfonly/p/6380860.html
 *
 * @author tigar
 * @date 2019/2/21.
 */

@Service
public class JmsService {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/61 * * * * ?")
    public void producer1() {
        jmsMessagingTemplate.convertAndSend("test_1", UUID.randomUUID().toString());
    }

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/63 * * * * ?")
    public void producer2() {
        UserDTO user = new UserDTO();
        user.setName(UUID.randomUUID().toString());
        user.setAge(UUID.randomUUID().hashCode());

        jmsMessagingTemplate.convertAndSend("test_2", user);
    }

    @JmsListener(destination = "test_1")
    public void consumer1(String msg) {
        System.out.println("activemq test_1 接收消息为:" + msg);
    }

    @JmsListener(destination = "test_2")
    public void consumer2(UserDTO msg) {
        System.out.println("activemq test_2 接收消息为:" + msg.toString());
    }
}

