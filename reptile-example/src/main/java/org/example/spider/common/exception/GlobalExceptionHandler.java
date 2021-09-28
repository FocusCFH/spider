package org.example.spider.common.exception;

import org.example.spider.common.result.template.ContentResultForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @ClassName GlobalExceptionHandler
 * @Description 处理接口全局异常
 * @Author chenfuhao
 * @Date 2021/9/27 17:07
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ContentResultForm exceptionHandler(Exception e){
        logger.error("error: ", e);
        return new ContentResultForm(false,e.getMessage(),"操作失败,请联系技术人员");
    }
}
