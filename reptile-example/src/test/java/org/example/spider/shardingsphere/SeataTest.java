package org.example.spider.shardingsphere;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.example.spider.domain.AfsConcessionAnalysis;
import org.example.spider.domain.Seata1;
import org.example.spider.domain.Seata2;
import org.example.spider.service.AfsConcessionAnalysisService;
import org.example.spider.service.Seata1Service;
import org.example.spider.service.Seata2Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @ClassName SeataTest
 * @Description
 * @Author chenfuhao
 * @Date 2022/3/10 14:38
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SeataTest {

    @Autowired
    private Seata1Service seata1Service;

    @Autowired
    AfsConcessionAnalysisService afsConcessionAnalysisService;


    @Test
    public void test() throws Exception {
        AfsConcessionAnalysis afsConcessionAnalysis = new AfsConcessionAnalysis();
        afsConcessionAnalysis.setId(UUID.randomUUID().toString());
        afsConcessionAnalysis.setAfterPosition("111$$$$1111");
        afsConcessionAnalysisService.save(afsConcessionAnalysis);
//        seata1Service.saveMa();
    }
}
