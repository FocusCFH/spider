package org.example.spider.config.kafka.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.spider.domain.BscUser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaConsumerListener
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/23 15:42
 * @Version 1.0
 **/
@Slf4j
@Component
public class KafkaConsumerListener {

    /**
     * kafka消费者
     * @param record
     */
    @KafkaListener(topics = {"#{'${kafka.consumer.topics}'.split(',')}"},groupId = "#{'${kafka.consumer.topics}'}")
    public void consumer(BscUser record){
        log.info("--------》消费者1接收主题数据：" + JSON.toJSONString(record));
    }

    /**
     * kafka消费者
     * @param record
     */
    @KafkaListener(topics = {"#{'${kafka.consumer.topics}'.split(',')}"},groupId = "#{'${kafka.consumer.topics}'}")
    public void consumer2(BscUser record){
        log.info("--------》消费者2接收主题数据：" + JSON.toJSONString(record));
    }
}
