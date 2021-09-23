package org.example.spider.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaConfigProperties
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/22 14:50
 * @Version 1.0
 **/
@Component
public class RabbitMQConfigProperties {

    /**
     * 简单模式队列名称
     */
    public static String mqDirectQueueName;

    /**
     * 简单模式交换机名称
     */
    public static String mqDirectExchangeName;

    /**
     * 简单模式路由键
     */
    public static String mqDirectRoutekeyName;

    @Value("${mq.direct.queue}")
    public void setMqDirectQueueName(String mqDirectQueueName) {
        RabbitMQConfigProperties.mqDirectQueueName = mqDirectQueueName;
    }

    @Value("${mq.direct.exchange}")
    public void setMqDirectExchangeName(String mqDirectExchangeName) {
        RabbitMQConfigProperties.mqDirectExchangeName = mqDirectExchangeName;
    }

    @Value("${mq.direct.routekey}")
    public void setMqDirectRoutekeyName(String mqDirectRoutekeyName) {
        RabbitMQConfigProperties.mqDirectRoutekeyName = mqDirectRoutekeyName;
    }
}
