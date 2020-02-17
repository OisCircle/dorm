package com.qcq.dorm.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

/**
 * @author O
 * @since 2020/2/17
 */
public class MpGeneratorUtil {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
//		doGeneration("bed","Bed");
//		doGeneration("building","Building");
//		doGeneration("dorm","Dorm");
//		doGeneration("dorm_change_form", "DormChangeForm");
//		doGeneration("dorm_officer", "DormOfficer");
//		doGeneration("fix_form", "FixForm");
//		doGeneration("item", "Item");
//		doGeneration("student", "Student");

    }

    private static void doGeneration(String tableName, String name) {
        String dir = "C:\\Users\\oisci\\IdeaProjects";
        String author = "O";
        String databaseName = "dorm";
//        String module = "comment";
//        String packageName = "dialogue";
        String username = "root";
        String password = "qq793414670";
        String host = "39.108.162.211";

        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(dir + "\\dorm\\"
//                + module
                + "\\src\\main\\java\\");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor(author);
        gc.setMapperName(name + "Mapper");
        gc.setControllerName(name + "Controller");
        gc.setXmlName(name + "Mapper");
        gc.setServiceName(name + "Service");
        gc.setServiceImplName(name + "ServiceImpl");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl("jdbc:mysql://" + host + ":3306/" + databaseName + "?characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setInclude(tableName);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.qcq.dorm");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        mpg.setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                    }
                }.setFileOutConfigList(Collections.singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        tableInfo.setEntityName(strategy, name);
                        return dir + "\\dorm\\" + "\\src\\main\\resources\\" + "mapper\\"
                                + "\\" + name + "Mapper.xml";
                    }
                })));

        // 执行生成
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);
        mpg.execute();
    }
}
