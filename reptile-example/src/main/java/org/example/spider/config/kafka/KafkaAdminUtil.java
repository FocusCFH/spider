package org.example.spider.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName KafkaAdminUtil
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/27 16:30
 * @Version 1.0
 **/
@Component
@Slf4j

public class KafkaAdminUtil {

    @Autowired
    private KafkaAdmin admin;

    /**
     * 创建一个adminClient链接
     * @return
     */
    public AdminClient createAdminClient(){
        AdminClient adminClient = null;
        Map<String, Object> config = admin.getConfig();
        try {
            adminClient = AdminClient.create(config);
        } catch (Exception e) {
            log.warn("创建kafka管理员客户端出错：" + e.getMessage());
        }
        return adminClient;
    }

    /**
     * 过滤系统中已经存在的主题，只创建不存在的主题
     * @param adminClient
     * @param newTopicNames
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Set<String> filterExistTopicName(AdminClient adminClient, Set<String> newTopicNames) throws ExecutionException, InterruptedException, TimeoutException {
        if (CollectionUtils.isEmpty(newTopicNames)) return null;
        // 获取已存在的topic列表
        Set<String> sysExistTopicNames = adminClient.listTopics().names().get(30L, TimeUnit.SECONDS);
        if (CollectionUtils.isEmpty(sysExistTopicNames)) return newTopicNames;
        // 取差值，即在A集合中排除B交集中的元素
        Collection<String> subtract = CollectionUtils.subtract(newTopicNames, sysExistTopicNames);
        if (CollectionUtils.isEmpty(subtract)) return null;
        Set<String> result = new HashSet<>();
        CollectionUtils.addAll(result,subtract);
        return result;
    }

    /**
     * 创建主题
     * @param adminClient
     * @param newTopicNames
     */
    public void addTopics(AdminClient adminClient, Set<String> newTopicNames){
        if (adminClient == null || CollectionUtils.isEmpty(newTopicNames)) return;
        List<NewTopic> topics = formatTopicNamesToNewTopics(newTopicNames);
        CreateTopicsResult topicResults = adminClient.createTopics(topics);
        try {
            topicResults.all().get(60L, TimeUnit.SECONDS);
        } catch (InterruptedException var9) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for topic creation results", var9);
        } catch (TimeoutException var10) {
            throw new KafkaException("Timed out waiting for create topics results", var10);
        } catch (ExecutionException var11) {
            log.error("Failed to create topics", var11.getCause());
            throw new KafkaException("Failed to create topics", var11.getCause());
        }
    }

    public Set<String> getExistTopics() {
        AdminClient adminClient = null;
        try {
            adminClient = createAdminClient();
            if (adminClient == null) return null;
            // 获取已存在的topic列表
            Set<String> sysExistTopicNames = adminClient.listTopics().names().get(30L, TimeUnit.SECONDS);
            return sysExistTopicNames;
        } catch (Exception e){
            log.error("加载topic异常，请检查kafka是否正常：" + e.getMessage());
            return null;
        }finally {
            if (adminClient != null) adminClient.close();
        }
    }

    public boolean deleteTopic(String topicName){
        AdminClient adminClient = null;
        try {
            adminClient = createAdminClient();
            if (adminClient == null) return false;
            List<String> topics = new ArrayList<>();
            topics.add(topicName);
            DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topics);
            return true;
        } catch (Exception e){
            log.error("删除topic异常，请检查kafka是否正常：" + e.getMessage());
            return false;
        }finally {
            if (adminClient != null) adminClient.close();
        }
    }

    /**
     * 格式化topic名称为NewTopic
     * @param newTopicNames
     * @return
     */
    private List<NewTopic> formatTopicNamesToNewTopics(Set<String> newTopicNames){
        List<NewTopic> topics = new ArrayList<>();
        for (String topicName : newTopicNames){
            NewTopic newTopic = new NewTopic(topicName,8, (short) 1);
            topics.add(newTopic);
        }
        return topics;
    }
}
