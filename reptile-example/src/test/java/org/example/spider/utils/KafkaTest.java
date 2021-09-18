package org.example.spider.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.spider.config.KafkaConfig;
import org.example.spider.domain.BscUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
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
public class KafkaTest {
    public static final String TOPIC_NAME = "system.log.topic.v1";

    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Test
    public void testRedis(){
        BscUser bscUser = new BscUser();
        bscUser.setId("1");
        bscUser.setName("陈富豪");
        kafkaTemplate.send(TOPIC_NAME,bscUser);
        log.info("-----------》生产者发送数据：" + JSON.toJSONString(bscUser));
    }
}
