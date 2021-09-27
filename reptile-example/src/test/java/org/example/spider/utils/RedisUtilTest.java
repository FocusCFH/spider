package org.example.spider.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.spider.config.redis.util.RedisUtil;
import org.example.spider.domain.BscUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName RedisUtilTest
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/16 17:20
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedis(){
        String key = "key--001";
        String value = "value-001";
        BscUser valueO = new BscUser();
        valueO.setName("chenfuhao");
        redisUtil.delK(key);
        boolean hasK1 = redisUtil.hasK(key);
        boolean set = redisUtil.set(key, valueO);
        Object o = redisUtil.get(key);
        boolean hasK = redisUtil.hasK(key);
        log.info("isSet - " + set + " , getValue - " + JSON.toJSONString(o) +" , " +"hasK1 - " + hasK1 + " , hasK - " + hasK) ;
    }

}
