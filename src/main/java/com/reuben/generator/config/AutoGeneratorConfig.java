package com.reuben.generator.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.reuben.generator.utils.TempPropsUtil;

import static com.reuben.generator.utils.TempPropsUtil.*;

public class AutoGeneratorConfig {
    /**
     * 设置自动生成配置信息
     * @return
     */
    public AutoGenerator getAutoGenerator(){
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局 相关配置
        autoGenerator.setGlobalConfig(getGlobalConfig());
        // 数据源配置
        autoGenerator.setDataSource(getDataSourceConfig());

        // ---------------------主要配置方
        // 模板 相关配置
        autoGenerator.setTemplate(getTemplateConfig());
        // 注入配置
        autoGenerator.setCfg(getInjectionConfig());
        // 数据库表配置
        autoGenerator.setStrategy(getStrategyConfig());
        // 包 相关配置
        autoGenerator.setPackageInfo(getPackageConfig());

        // 模板引擎 --> 不指定默认用Velocity

        return autoGenerator;
    }

    /**
     * 设置全局常量配置信息: 作者、输出路径、文件名称格式化等
     * @return
     */
    private GlobalConfig getGlobalConfig(){
        GlobalConfig gc = new GlobalConfig();
        // 文件输出路径
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        // 文件作者
        gc.setAuthor(TempPropsUtil.getProp(AUTHOR));
        // 是否覆盖现有文件
        gc.setFileOverride(true);
        // 是否打开输出目录文件夹
        gc.setOpen(false);
        // 是否在xml中添加二级缓存配置
        gc.setEnableCache(false);
        // 是否开启 swagger2 模式
        gc.setSwagger2(TempPropsUtil.isSwagger2());
        // 开启 BaseResultMap. 生成通用result映射关系
        gc.setBaseResultMap(true);
        // 开启 baseColumnList. 生成通用列xml的sql
        gc.setBaseColumnList(true);
        // 设置生成文件名称格式
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.TIME_PACK);
        return gc;
    }

    /**
     * 设置数据源信息: 数据库类型和连接信息
     * @return
     */
    private DataSourceConfig getDataSourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        // 设置数据库连接
        dsc.setUrl(TempPropsUtil.getProp(DATASOURCE_URL));
        dsc.setDriverName(TempPropsUtil.getProp(DATASOURCE_DRIVER));
        dsc.setUsername(TempPropsUtil.getProp(DATASOURCE_NAME));
        dsc.setPassword(TempPropsUtil.getProp(DATASOURCE_PASSWORD));

        // 设置数据库类型
        dsc.setDbType(TempPropsUtil.dbType());
        return dsc;
    }

    /**
     * 包配置: 父包名和子包名重定义
     */
    private PackageConfig getPackageConfig(){
        PackageConfig pc = new PackageConfig();
        // 父包模块名
        pc.setModuleName(null);
        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(TempPropsUtil.packageName());

        // 设置mapper.xml和mapper接口同一个包
        pc.setXml("mapper");

        // 设置entity包名为model
        pc.setEntity("model");
        return pc;
    }
    /**
     * 返回加载VM模板文件的模板配置信息
     * @return
     */
    private TemplateConfig getTemplateConfig(){
        //指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("/temp/vm/controller.java");
        templateConfig.setService("/temp/vm/service.java");
        templateConfig.setServiceImpl("/temp/vm/serviceImpl.java");
        templateConfig.setEntity("/temp/vm/entity.java");
        templateConfig.setMapper("/temp/vm/mapper.java");
        templateConfig.setXml("/temp/vm/mapper.xml");
        return templateConfig;
    }

    /**
     * 自定义生成策略: 名称转换、表包含、表排除、实体类超类指定
     * @return
     */
    private StrategyConfig getStrategyConfig(){
        StrategyConfig st = new StrategyConfig();

        // 设置表名和属性名 -- 名称下划线转驼峰
        st.setNaming(NamingStrategy.underline_to_camel);
        st.setColumnNaming(NamingStrategy.underline_to_camel);
        // 设置当前生成表前缀(要去除的)
        String tablePrefixsStr = TempPropsUtil.getProp("table.prefixs");
        if(StringUtils.isNotBlank(tablePrefixsStr)){
            String[] tablePrefixsArr = tablePrefixsStr.split(StringPool.COMMA);
            if(tablePrefixsArr.length > 0){
                st.setTablePrefix(tablePrefixsArr);
            }
        }

        // 设置包含的表名
        String tableIncludeStr = TempPropsUtil.getProp("table.incloude");
        if(StringUtils.isNotBlank(tableIncludeStr)){
            String[] tableIncludeArr = tableIncludeStr.split(StringPool.COMMA);
            if(tableIncludeArr.length > 0){
                st.setInclude(tableIncludeArr);
            }
        }

        // 设置排除的表名
        String tableExcludeStr = TempPropsUtil.getProp("table.exclude");
        if(StringUtils.isNotBlank(tableExcludeStr)){
            String[] tableExcludeArr = tableExcludeStr.split(StringPool.COMMA);
            if(tableExcludeArr.length > 0){
                st.setExclude(tableExcludeArr);
            }
        }

        // 设置基础实体类路径名和基础实体类字段(数据库字段)
        String superEntity = TempPropsUtil.getProp("super.entity");
        if(StringUtils.isNotBlank(superEntity)){
            st.setSuperEntityClass(superEntity);
            String superEntityColumns = TempPropsUtil.getProp("super.entity.columns");
            if(StringUtils.isNotBlank(superEntityColumns)){
                st.setSuperEntityColumns(superEntityColumns.split(StringPool.COMMA));
            }
        }

        // 设置为lombok模式
        Boolean lombokModel = Boolean.valueOf(TempPropsUtil.getProp("lombok.model"));
        st.setEntityLombokModel(lombokModel);

        // @RestController控制器
        st.setRestControllerStyle(true);
        st.setControllerMappingHyphenStyle(true);

        // 是否生成实体时，生成字段注解
        st.setEntityTableFieldAnnotationEnable(true);

        return st;
    }

    /**
     * 注入自定义配置属性
     */
    private InjectionConfig getInjectionConfig(){
       InjectionConfig injectionConfig = new CustomInjectionConfig();

       // 设置全局参数
       injectionConfig.initMap();

       // 加入其他自定义文件输出: 设置vm生成模板;设置对应模板生成文件的输出路径
       // 自定义wrapper/其他bo、vo、dto类型

       return injectionConfig;
    }



}
