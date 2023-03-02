package org.example.spider.config.mq.sender;

//import org.example.spider.domain.BscUser;
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
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public boolean sendDirectMsg(String exchangeName, String routeKey,BscUser bscUser){
//        rabbitTemplate.convertAndSend(exchangeName,routeKey,bscUser);
//        return true;
//    }

}
