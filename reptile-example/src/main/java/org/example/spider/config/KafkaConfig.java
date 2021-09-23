package org.example.spider.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.spider.domain.BscUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName KafkaConfig
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/17 13:40
 * @Version 1.0
 **/
@Configuration
@Slf4j
@EnableKafka
public class KafkaConfig {

    public static final String TOPIC_NAME = "system.log.topic.v1";


    /**
     *  创建一个名为TOPIC_NAME的Topic并设置分区数为8，分区副本数为2
     * @return
     */
    @Bean
    public NewTopic initialTopic(){
        log.info("------------------- 初始化系统日志kafka主题 ------------------------");
        return new NewTopic(TOPIC_NAME,8, (short) 2);
    }

//    /**
//     * 如果要修改分区数，只需修改配置值重启项目即可
//     * 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
//     * @return
//     */
//    @Bean
//    public NewTopic updateTopic(){
//        return new NewTopic(TOPIC_NAME,10, (short) 2);
//    }


}
