package org.example.spider.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.spider.config.kafka.KafkaAdminUtil;
import org.example.spider.config.kafka.KafkaConfig;
import org.example.spider.config.mq.RabbitMQConfigProperties;
import org.example.spider.domain.BscUser;
import org.example.spider.config.kafka.sender.KafkaProducerSender;
import org.example.spider.config.mq.sender.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @ClassName TestcONTROLLER
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/18 16:02
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {

    public static String TOPIC_NAME = "system.log.topic.v1";

    @Autowired
    private KafkaProducerSender kafkaProducerSender;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private KafkaAdminUtil kafkaAdminUtil;

    @GetMapping(value = "/kafka")
    public String testKafka(){
        BscUser bscUser = new BscUser();
        bscUser.setName("陈富豪");
        for (int i = 0; i < 20 ; i ++){
            bscUser.setId(String.valueOf(i));
            kafkaProducerSender.producer(TOPIC_NAME,bscUser);
        }
        return "发送完成";
    }

    @GetMapping(value = "/kafka/getExistTopics")
    public Set<String> getExistTopics(){
        return kafkaAdminUtil.getExistTopics();
    }

    @GetMapping(value = "/kafka/deleteTopic")
    public boolean deleteTopic(String topicName){
        return kafkaAdminUtil.deleteTopic(topicName);
    }



    @GetMapping(value = "/rabbitmq")
    public String testRabbitmq(){
        BscUser bscUser = new BscUser();
        bscUser.setId("11");
        bscUser.setName("陈富豪1");
        rabbitMQSender.sendDirectMsg(RabbitMQConfigProperties.mqDirectExchangeName,RabbitMQConfigProperties.mqDirectRoutekeyName,bscUser);
        log.info("-----------》发送队列消息1：" + JSON.toJSONString(bscUser));

        bscUser.setId("12");
        bscUser.setName("陈富豪2");
        rabbitMQSender.sendDirectMsg(RabbitMQConfigProperties.mqDirectExchangeName,RabbitMQConfigProperties.mqDirectRoutekeyName+1,bscUser);
        log.info("-----------》发送队列消息2：" + JSON.toJSONString(bscUser));
        return JSON.toJSONString(bscUser);
    }



}
