package org.example.spider.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;

/**
 * @ClassName RedisUtil
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/16 16:44
 * @Version 1.0
 **/
@Component
public class RedisUtil {



    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增一个 k - v
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 新增一个 k - v （带失效时间）
     * @param key     键
     * @param value   值
     * @param expire  失效时间 ms
     * @return
     */
    public boolean set(String key,Object value,long expire){
        try{
            redisTemplate.opsForValue().set(key,value,expire);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 根据 k 获取 v
     * @param key 键
     * @return
     */
    public Object get(String key){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            return o;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return
     */
    public boolean hasK(String key){
        try{
            Boolean o = redisTemplate.hasKey(key);
            return o;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 删除key
     * @param key 键
     * @return
     */
    public boolean delK(String key){
        try{
            Boolean o = redisTemplate.delete(key);
            return o;
        }catch (Exception e){
            return false;
        }
    }
}
