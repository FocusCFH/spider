package org.example.spider.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.spider.config.properties.RabbitMQConfigProperties;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMQListener
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/22 16:14
 * @Version 1.0
 **/
@Component
@Slf4j
public class RabbitMQDirectListenerHandler implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("====接收到" + message.getMessageProperties().getConsumerQueue() + "队列的消息=====");
        log.info(message.getMessageProperties().toString());
        log.info(new String(message.getBody()));
    }
}
