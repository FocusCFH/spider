package org.example.spider.tool;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MybatisPlusCodeGenerator
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/15 14:29
 * @Version 1.0
 **/
public class MybatisPlusCodeGenerator {

    public static void main(String[] args) {
        // *************************** 属性变量 ***********************************************
        // 文件输出路径是当前项目下路径
        String outputDir = "/src/main/java";
        // 作者
        String author = "chenfuhao";
        // --------------------------- 数据源 ------------------
//        String url = "jdbc:dm://www.heilongjiang.local:50309/AREA_RECONSTRUCTION";
//        String driverName = "dm.jdbc.driver.DmDriver";
//        String username = "AREA_RECONSTRUCTION";
//        String password = "pA55w0rd44";

//        String url = "jdbc:postgresql://dev.heilongjiang.local:50343/cim_data";
//        String driverName = "org.postgresql.Driver";
//        String username = "postgres";
//        String password = "a5e35d4bb6a8";

        String url = "jdbc:postgresql://172.18.19.224:5432/agcim_first_stage";
        String driverName = "org.postgresql.Driver";
        String username = "postgres";
        String password = "a5e35d4bb6a8";
        // -------------------- 生成的java 文件保存路径 ---------
        String mapper = "org.example.spider.mapper";
        String domain = "org.example.spider.domain";
        String service = "org.example.spider.service";
        String serviceImpl = "org.example.spider.service.impl";
        // -------------------- 生成xml保存路径 ----------------
        String xml = "/src/main/resources/org/example/spider/mapper/";
        // 需要自动生成代码的数据库表名
        String[] tables = {"ass_model_land_re","ass_model_user_org_re"};
        // ***********************************************************************************

        //代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + outputDir);
        gc.setAuthor(author);
        gc.setServiceName("%sService");//自定义Service接口生成的文件名
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setFileOverride(false); // 是否覆盖
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setMapper(mapper);
        pc.setEntity(domain);
        pc.setService(service);
        pc.setServiceImpl(serviceImpl);
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                //to do nothing
            }
        };

        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义配置会优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + xml  + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        //配置策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);// 默认是false,自动lombok

        strategy.setInclude(tables);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
