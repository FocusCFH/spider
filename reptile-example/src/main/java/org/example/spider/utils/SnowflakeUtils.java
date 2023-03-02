package org.example.spider.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @ClassName SnowflakeUtils
 * @Description
 * @Author chenfuhao
 * @Date 2022/2/15 15:33
 * @Version 1.0
 **/
public class SnowflakeUtils {

    public synchronized static long getSnowflakeId(){
        Snowflake snowflake = IdUtil.getSnowflake();
        long id = snowflake.nextId();
        return id;
    }

//    public static void main(String[] args){
//        for (int i = 0 ; i < 100 ; i++) {
//            System.out.println(SnowflakeUtils.getSnowflakeId());
//        }
//    }

}
