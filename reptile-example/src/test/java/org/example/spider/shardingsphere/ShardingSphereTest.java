package org.example.spider.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName ShardingSphereTest
 * @Description
 * @Author chenfuhao
 * @Date 2022/2/16 16:14
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingSphereTest {

//    @Autowired
//    private AssAgmComponentInfoService assAgmComponentInfoService;
//
//    @Autowired
//    private AfsLandSysItemReService afsLandSysItemReService;
//
//    @Autowired
//    private AssAgmRegionInfoService assAgmRegionInfoService;

    @Test
    public void test(){
//        List<AfsLandSysItemRe> allByLandSysPurpose = afsLandSysItemReService.getAllByLandSysPurpose("d32eecc991cc6d717f3a56a9c3242693", "6faa2c419bbbbede", "9d896d8b1b549694");
//        System.out.println();
//        List<AssAgmComponentInfo> assAgmComponentInfos = new ArrayList<>();
//        for (int j = 0 ; j < 2; j ++) {
//            Long sliceTableId = SnowflakeUtils.getSnowflakeId();
//            for (int i = 0; i < 50; i++) {
//                AssAgmComponentInfo assAgmComponentInfo = new AssAgmComponentInfo();
//                assAgmComponentInfo.setSliceTableId(sliceTableId);
//                assAgmComponentInfo.setObjectId((long) i);
//                assAgmComponentInfos.add(assAgmComponentInfo);
//            }
//        }
//
//        assAgmComponentInfoService.saveBatch(assAgmComponentInfos);
//
//        List<AssAgmRegionInfo> assAgmRegionInfos = new ArrayList<>();
//        for (int j = 0 ; j < 2; j ++) {
//            Long sliceTableId = SnowflakeUtils.getSnowflakeId();
//            for (int i = 0; i < 50; i++) {
//                AssAgmRegionInfo assAgmRegionInfo = new AssAgmRegionInfo();
//                assAgmRegionInfo.setSliceTableId(sliceTableId);
//                assAgmRegionInfo.setObjectId(i);
//                assAgmRegionInfos.add(assAgmRegionInfo);
//            }
//        }
//
//        assAgmRegionInfoService.saveBatch(assAgmRegionInfos);
    }
}
