package com.reuben.generator.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import static com.reuben.generator.constant.Attribute.RESOURCE_PATH;

public class TempPropsUtil {

    public static String DATASOURCE_URL = "spring.datasource.url";
    public static String DATASOURCE_DRIVER = "spring.datasource.driver-class-name";
    public static String DATASOURCE_NAME = "spring.datasource.username";
    public static String DATASOURCE_PASSWORD = "spring.datasource.password";
    public static String PACKAGE_NAME = "package.name";


    public static String AUTHOR = "author";

    public static Properties getTempProps(){
        Resource resource = new ClassPathResource(RESOURCE_PATH);
        Properties properties = new Properties();
        try{
            properties = PropertiesLoaderUtils.loadProperties(resource);
        }catch (IOException e){
            e.printStackTrace();
        }
        return properties;
    }

    public static String getProp(String propName){
        return (String)getTempProps().get(propName);
    }

    public static Boolean isSwagger2(){
        String swagger = getProp("is-swagger2");
        return StringUtils.isBlank(swagger)? Boolean.TRUE : Boolean.valueOf(swagger);
    }

    public static DbType dbType(){
        String type = getProp("db-type");
        return StringUtils.isBlank(type)? DbType.MYSQL : DbType.getDbType(type);
    }

    public static String packageName(){
        String packageName = getProp(PACKAGE_NAME);
        return StringUtils.isBlank(packageName)? "com.demo.reuben" : packageName;
    }
}
