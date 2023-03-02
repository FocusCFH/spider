package org.example.spider.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.spider.domain.Seata1;
import org.example.spider.domain.Seata2;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenfuhao
 * @since 2022-03-10
 */
@DS("slave_1")
public interface Seata2Service extends IService<Seata2> {

}
