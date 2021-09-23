package org.example.spider.utils;

import org.example.spider.config.properties.RabbitMQConfigProperties;
import org.example.spider.domain.BscUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMQUtil
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/22 14:33
 * @Version 1.0
 **/
@Component
public class RabbitMQUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean sendDirectMsg(BscUser bscUser){
        rabbitTemplate.convertAndSend(RabbitMQConfigProperties.mqDirectExchangeName,RabbitMQConfigProperties.mqDirectRoutekeyName,bscUser);
        return true;
    }

}
