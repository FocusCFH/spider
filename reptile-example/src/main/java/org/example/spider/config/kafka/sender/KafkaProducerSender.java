package org.example.spider.config.kafka.sender;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.spider.domain.BscUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaProducerSender
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/23 15:49
 * @Version 1.0
 **/
@Component
@Slf4j
public class KafkaProducerSender {

    @Autowired
    private KafkaTemplate<String, BscUser> kafkaTemplate;

    /**
     * kafka生产者
     * @param topicName
     * @param bscUser
     * @return
     */
    public boolean producer(String topicName, BscUser bscUser){
        kafkaTemplate.send(topicName,bscUser);
        log.info("-----------》生产者发送数据：" + JSON.toJSONString(bscUser));
        return true;
    }
}
