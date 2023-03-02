package org.example.spider.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName KafkaConfig
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/17 13:40
 * @Version 1.0
 **/
//@Configuration
//@Slf4j
//@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaAdminUtil kafkaAdminUtil;

    @Value("#{'${kafka.consumer.topics}'.split(',')}")
    private Set<String> systemTopicNames;

    @PostConstruct
    public void initSystemTopics() {

        if (CollectionUtils.isEmpty(systemTopicNames)) return;
        AdminClient adminClient = null;
        try{
            adminClient = kafkaAdminUtil.createAdminClient();
            if (adminClient == null) return;

            Set<String> shouldAddTopicNames = kafkaAdminUtil.filterExistTopicName(adminClient, systemTopicNames);
            if (CollectionUtils.isEmpty(shouldAddTopicNames)) return;
            kafkaAdminUtil.addTopics(adminClient,shouldAddTopicNames);
        } catch ( Exception e){
            e.printStackTrace();
        }finally {
            if(adminClient != null){
                adminClient.close(10L, TimeUnit.SECONDS);
            }
        }
    }



}
