package org.example.spider.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.example.spider.config.mq.listener.RabbitMQListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitMqConfig
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/22 11:43
 * @Version 1.0
 **/
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Autowired
    private AmqpAdminUtil amqpAdminUtil;

    /**
     * rabbitMQ队列初始化
     */
    @PostConstruct
    public void initQueue(){
        amqpAdminUtil.creatDirectWorker(RabbitMQConfigProperties.mqDirectQueueName,RabbitMQConfigProperties.mqDirectExchangeName,RabbitMQConfigProperties.mqDirectRoutekeyName);
    }
    // -------------------------------------------------------------------------------------------------------------

    /**
     * 队列监听器配置
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(new RabbitMQListener());
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put(RabbitMQConfigProperties.mqDirectQueueName, RabbitMQConfigProperties.mqDirectListenerMethodName); // 队列跟方法绑定
        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);


        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMQConfigProperties.mqDirectQueueName);
        container.setMessageListener(adapter);
        return container;
    }
}
