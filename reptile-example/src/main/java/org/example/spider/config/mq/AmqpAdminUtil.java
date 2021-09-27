package org.example.spider.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.example.spider.config.mq.common.ExchangeTypeEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMQManageUtil
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/27 14:48
 * @Version 1.0
 **/
@Component
@Slf4j
public class AmqpAdminUtil {

    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 工作模式（简单模式），在发送消息时必需同事指定routeKey与exchange，只有绑定到该exchange且routeKey匹配的队列才会接收到消息
     * @param queueName
     * @param exchangeName
     * @param routeKey
     */
    public void creatDirectWorker(String queueName,String exchangeName,String routeKey) {
        createQueue(queueName);
        createExchange(exchangeName, ExchangeTypeEnum.DIRECT);
        createBinding(queueName,exchangeName,routeKey);
        System.out.println();
    }

    /**
     * 广播模式，在发送消息时无需指定routeKey,只需要指定exchange即可，所有绑定到该exchange的队列都会接收到发布的消息
     * @param queueName
     * @param exchangeName
     * @param routeKey
     */
    public void createFanoutWorker(String queueName,String exchangeName,String routeKey) {
        createQueue(queueName);
        createExchange(exchangeName,ExchangeTypeEnum.FANOUT);
        createBinding(queueName,exchangeName,routeKey);
    }

    /**
     * 主题模式，在发送消息时可以指定准确的routeKey（mq.direct.routeKey），也可以使用通配符（mq.direct.*），如果带通配符的佩佩准确的routeKey，那带通配符的也会接收到准确的routeKey消息
     * @param queueName
     * @param exchangeName
     * @param routeKey
     */
    public void createTopicWorker(String queueName,String exchangeName,String routeKey) {
        createQueue(queueName);
        createExchange(exchangeName,ExchangeTypeEnum.TOPIC);
        createBinding(queueName,exchangeName,routeKey);
    }
    // ------------------------------------ private method ------------------------------------------------------------
    /**
     * 创建队列
     * @param queueName
     * @return
     */
    private boolean createQueue(String queueName){
        // 队列名称     是否持久化         是否私有化           是否自动删除              队列里的参数
        // String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        Queue queue = new Queue(queueName,false,false,true,null);
        String res = amqpAdmin.declareQueue(queue);
        log.info("队列" + queueName + "创建结果：" + res);
        return true;
    }

    /**
     * 创建交换机
     * @param exchangeName
     * @param exchangeType
     * @return
     */
    private boolean createExchange(String exchangeName, ExchangeTypeEnum exchangeType){
        if (exchangeType.equals(ExchangeTypeEnum.DIRECT)) {
            createDirectExchange(exchangeName);
            return true;
        }
        else if(exchangeType.equals(ExchangeTypeEnum.FANOUT)) {
            createFanoutExchange(exchangeName);
            return true;
        }
        else if (exchangeType.equals(ExchangeTypeEnum.TOPIC)) {
            createTopicExchange(exchangeName);
            return true;
        }
        return false;
    }

    /**
     * 队列与交换机绑定关系
     * @param queue
     * @param exchange
     * @param routeKey
     * @return
     */
    private boolean createBinding(String queue,String exchange,String routeKey){
        Binding binding = new Binding(queue,Binding.DestinationType.QUEUE,exchange,routeKey,null);
        amqpAdmin.declareBinding(binding);
        return true;
    }

    private void createDirectExchange(String exchangeName){
        //  String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
        DirectExchange directExchange = new DirectExchange(exchangeName,false,true,null);
        amqpAdmin.declareExchange(directExchange);
    }

    private void createFanoutExchange(String exchangeName){
        FanoutExchange fanoutExchange = new FanoutExchange(exchangeName,false,true,null);
        amqpAdmin.declareExchange(fanoutExchange);
    }

    private void createTopicExchange(String exchangeName){
        TopicExchange topicExchange = new TopicExchange(exchangeName,false,true,null);
        amqpAdmin.declareExchange(topicExchange);
    }
}
