package org.example.spider.config.mq.common;

/**
 * @ClassName ExchangeTypeEnum
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/27 14:46
 * @Version 1.0
 **/
public enum ExchangeTypeEnum  {
    DIRECT("direct"),
    FANOUT("fanout"),
    TOPIC("topic")
    ;

    private String name;

    ExchangeTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

