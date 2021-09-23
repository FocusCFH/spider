package org.example.spider.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.spider.domain.BscUser;
import org.example.spider.utils.RabbitMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public static final String TOPIC_NAME = "system.log.topic.v1";

    @Autowired
    private RabbitMQUtil rabbitMQUtil;


    @Autowired
    private KafkaTemplate<String,BscUser> kafkaTemplate;

    @GetMapping(value = "/kafka")
    public String testKafka(){
        BscUser bscUser = new BscUser();
        bscUser.setId("1");
        bscUser.setName("陈富豪");
        kafkaTemplate.send(TOPIC_NAME,bscUser);
        log.info("-----------》生产者发送数据：" + JSON.toJSONString(bscUser));
        return JSON.toJSONString(bscUser);
    }

    @KafkaListener(topics = {TOPIC_NAME},groupId = "system.log.topic")
    public void consumer(BscUser record){
        log.info("--------》消费者接收主题数据：" + JSON.toJSONString(record));
    }

    @GetMapping(value = "/rabbitmq")
    public String testRabbitmq(){
        BscUser bscUser = new BscUser();
        bscUser.setId("11");
        bscUser.setName("陈富豪1");
        rabbitMQUtil.sendDirectMsg(bscUser);
        log.info("-----------》发送队列消息：" + JSON.toJSONString(bscUser));
        return JSON.toJSONString(bscUser);
    }



}
