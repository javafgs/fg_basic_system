package com.fg.system.generater;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.StringWriter;
import java.util.*;

public class MyBatisPlusGenetater {
    public static void main(String[] args) throws FileNotFoundException {
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath:-----"+projectPath);
        String module_name = scanner("api所属模块名，多层模块用/分割");
        String[] table_name = scanner("表名,多个英文逗号分割").split(",");
        String[] msg_name = scanner("表对应的中文名,多个英文逗号分割").split(",");
        String[] table_prefix = scanner("要去掉的表前缀(不用的话,输入0直接回车跳过),多个英文逗号分割").split(",");
        //多自定义属性，多表，生成代码
        for(int i=0;i<table_name.length;i++){
                AutoGenerator ag = new AutoGenerator();
                ag.setGlobalConfig(doInitGlobalConfig(projectPath));
                ag.setDataSource(doInitDataSourceConfig(projectPath));
                ag.setTemplate(doInitTemplateConfig());
            if(table_prefix.length==1){
                ag.setStrategy(doInitStrategyConfig(table_name[i],table_prefix[0]));
            }else{
                ag.setStrategy(doInitStrategyConfig(table_name[i],table_prefix[i]));
            }
                ag.setPackageInfo(doInitPackageConfig(projectPath,module_name));
                ag.setCfg(doInitInjectionConfig(projectPath,module_name,msg_name[i]));
                ag.execute();
            }
    }
    /**
     * 读取控制台内容信息
     */
    private static String scanner(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + msg + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StringUtils.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + msg + "！");
    }
    //全局配置
    public static GlobalConfig doInitGlobalConfig(String projectPath) {
        String outputDir=getProMsg(projectPath,"outputDir");
        GlobalConfig gc = new GlobalConfig();
        //设置文件存放地方
        gc.setOutputDir(outputDir);//文件输出路径
        gc.setFileOverride(true);//文件是够覆盖更新
        gc.setAuthor("wfg");// 设置作者
        gc.setOpen(false);// 生成后打开目录
        gc.setBaseResultMap(true);//开启通用查询映射结果
        gc.setBaseColumnList(true);//开启通用查询结果列
        gc.setSwagger2(true);//开启Swagger2接口文档
        //下面控制输出文件的名字，可以自己定义
        gc.setEntityName("%s");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        //gc.setXmlName(table_name);
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setDateType(DateType.ONLY_DATE);// 设置时间类型使用哪个包下的
        return gc;
    }
    //配置数据源
    public static DataSourceConfig doInitDataSourceConfig(String projectPath) {
        String url=getProMsg(projectPath,"dataSource.url");
        String drivename=getProMsg(projectPath,"dataSource.driverName");
        String username=getProMsg(projectPath,"dataSource.username");
        String password=getProMsg(projectPath,"dataSource.password");
        DataSourceConfig dsc = new DataSourceConfig();
        //设置数据库
        dsc.setUrl(url);
        dsc.setDriverName(drivename);
        dsc.setUsername(username);
        dsc.setPassword(password);
        return dsc;
    }
    //策略配置
    public static StrategyConfig doInitStrategyConfig(String tableNames,String table_prefix) {
        StrategyConfig sc = new StrategyConfig();
        //去掉表前缀，可以是数组
        if(!"0".equals(table_prefix)){
            sc.setTablePrefix(table_prefix);
        }
        // 表名生成策略(下划线转驼峰)
        sc.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表名，可以是数组
        sc.setInclude(tableNames);
        //sc.setSuperServiceClass(null);
        //sc.setSuperServiceImplClass(null);
        sc.setSuperMapperClass(null);
        return sc;
    }
    //包名配置
    public static PackageConfig doInitPackageConfig(String projectPath,String module_name) {
        PackageConfig pc = new PackageConfig();
        String parent="";
        String last_module_name="";
        if(module_name.indexOf("/")!=-1){
            int index = module_name.lastIndexOf("/");
            String parent_name  = module_name.substring(0, index+1).replace("/","");
             last_module_name=module_name.substring(index+1);
             parent=getProMsg(projectPath,"package.base")+"."+parent_name;
        }else{
            parent=getProMsg(projectPath,"package.base");
            last_module_name=module_name;
        }
        pc.setParent(parent) // 设置父包
                .setModuleName(last_module_name)//设置模块包名
                .setMapper("dao");//包名需要变化的可以自定义名称
                /*.setService("service")
                .setController("controller")
                .setEntity("pojo");*/
        return pc;
    }
    //自定义模板
    public static TemplateConfig doInitTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        //resource下的模板想要修改可以自己改动
        tc.setController("/vm/controller.java.vm");
        tc.setService("/vm/service.java.vm");
        tc.setServiceImpl("/vm/serviceImpl.java.vm");
        tc.setEntity("/vm/entity.java.vm");
        tc.setMapper("/vm/mapper.java.vm");
        tc.setXml(null);//这里要设置成null，才不会自己生成


        return tc;
    }

    /**
     * 自定义模板路径配置
     */
    private static InjectionConfig doInitInjectionConfig(String projectPath, String module_name,String msgName) {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可用于自定义属性,模板里用${cfg.msgName}来获取值
                Map<String, Object> map = new HashMap<>();
                map.put("msgName", msgName);
                this.setMap(map);
            }
        };
        // 模板引擎是Velocity
        String templatePath = "/vm/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、tableInfo.getEntityName()获取了实体类的名称，所以xml的名称会跟着发生变化的
                return projectPath + "/src/main/resources/mapper/" + module_name
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }
    /**
     * 自定义模板属性
     */
    private static InjectionConfig doInitInjectionConfigParams(String msgName) {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可用于自定义属性,模板里用${cfg.msgName}来获取值
                Map<String, Object> map = new HashMap<>();
                map.put("msgName", msgName);
                this.setMap(map);
            }
        };
        return injectionConfig;
    }
    //获取代码生成的配置文件
    public static String getProMsg(String projectPath,String key){
        try {
            // 创建properties集合类
            Properties properties = new Properties();
            properties.load(new FileReader(projectPath+"/src/main/resources/generator.properties"));
            // 获取数据、赋值
            String value = properties.getProperty(key);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void doTmp(){
        VelocityEngine engine = new VelocityEngine();// 定义模板引擎
        Properties properties = new Properties();// 模板引擎属性
        properties.setProperty(Velocity.RESOURCE_LOADER, "file");// 定义资源加载器类型，此处为file
        String path="E:/ideagen/fg_basic_system/src/main/resources/vm";
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path); // 定义文件加载路径path
        engine.init(properties);
        VelocityContext ctx = new VelocityContext();
        ctx.put("msgName", "666");
        Template tem = null;
        tem = engine.getTemplate("controller.java.vm");
        StringWriter writer = new StringWriter();
        tem.merge(ctx, writer);
        //System.out.println(writer.toString());
    }
}
