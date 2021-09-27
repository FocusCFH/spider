package org.example.spider.config.mq.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RabbitMQListener
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/27 14:31
 * @Version 1.0
 **/
@Slf4j
public class RabbitMQListener {

    public void directListener(Object message){
        log.info("--------》directListener接收到的对象：" + JSON.toJSONString(message));
    }

}
