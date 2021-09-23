package org.example.spider.config;

import org.example.spider.config.properties.RabbitMQConfigProperties;
import org.example.spider.listener.RabbitMQDirectListenerHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitMqConfig
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/22 11:43
 * @Version 1.0
 **/
@Configuration
public class RabbitMqConfig {
    @Autowired
    private RabbitMQDirectListenerHandler rabbitMQDirectListenerHandler;
    /**
     * rabbitmq有五种模式：
     * 1、简单模式：消息产生者产生消息，消息的消费者进行消费
     * 2、工作模式：消息消费产生消息，将消息发送到消息队列中，这是竞争，消费者1和消费者2都监听消息队列，当队列中有消息，一起来抢消息。谁抢到谁处理。
     * 3、消息发布与订阅模式：消息产生者产生消息，将消息发送到交换机中。多个消息队列绑定到交换机上。交换机将消息发送到多个队列中。消费者1监听自己的队列，如果有消息就进行消费。消费者2监听自己的队列，如果有消息进行消费
     * 4、路由模式：比发布订阅模式多了一个路由选择，称为路由key。路由key指定一个名称。队列在绑定到交换机时，还要设置这个路由key。消息的队列中不是所有的消息了，交换机会根据消息的路由key，选择性将消息传递给消息队列。
     * 5、主题模式：在路由模式基础上，让路由key可以使用通配符。相当于进行分类。灵活程度更高些。隐患：容易误伤。
     */

    // ----------------------------------------简单模式-----------------------------------------------------------------
    /**
     * 创建队列
     */
    @Bean(name = "DirectQueue")
    public Queue DirectQueue(){
        return new Queue(RabbitMQConfigProperties.mqDirectQueueName,true);
    }
    /**
     * 创建交换机
     */
    @Bean(name = "DirectExchange")
    public DirectExchange DirectExchange(){
        return new DirectExchange(RabbitMQConfigProperties.mqDirectExchangeName,true,false);
    }
    /**
     * 队列与交换机进行绑定
     */
    @Bean(name = "DirectBinding")
    public Binding DirectBinding(){
        return BindingBuilder.bind(DirectQueue()).to(DirectExchange()).with(RabbitMQConfigProperties.mqDirectRoutekeyName);
    }
    // ----------------------------------------工作模式-----------------------------------------------------------------

    // ----------------------------------------消息发布与订阅模式--------------------------------------------------------

    // ----------------------------------------路由模式-----------------------------------------------------------------

    // ----------------------------------------主题模式-----------------------------------------------------------------

    // ----------------------------------------动态设置监听器 ----------------------------------------------------------
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMQConfigProperties.mqDirectQueueName);
        container.setMessageListener(rabbitMQDirectListenerHandler);
        return container;
    }
}
