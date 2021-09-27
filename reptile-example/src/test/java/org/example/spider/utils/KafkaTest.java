package org.example.spider.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.spider.domain.BscUser;
import org.example.spider.config.kafka.sender.KafkaProducerSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName KafkaTest
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/18 15:54
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KafkaTest{

    @Autowired
    KafkaProducerSender kafkaProducerSender;

    @Test
    public void testRedis(){
        BscUser bscUser = new BscUser();
        bscUser.setName("陈富豪");
        for (int i = 0; i < 20 ; i ++){
            bscUser.setId(String.valueOf(i));
            kafkaProducerSender.producer("system.log.topic.v1",bscUser);
        }
    }
}
