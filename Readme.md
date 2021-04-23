## 使用步骤
> 1. 使用前首先在resources/temp下创建code.properties文件
> 2. 修改对应内容.下有内容信息介绍
> 3. 启动代码构建`GeneratorTool.main`方法

## 内容信息介绍
```text
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://{数据库地址}/{数据库名}?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
spring.datasource.username=用户名
spring.datasource.password=密码
author=作者
# 是否开启swaggger2模式.生成swaggger注解
is-swagger2=true
# 支持类型: mysql、oracle、h2、sqlserver、mariadb | 需要引入对应连接驱动
db-type=mysql

# 生成代码的包名前缀例如com.demo
package.name=

# 表需要去除前缀; 生成表;去除表;
table.prefixs=prefix_
table.incloude=prefix_table1,prefix_table2
table.exclude=prefix_exclude1,prefix_exclude2

# 设置自定义的超类全路径名(需在pom中添加自定义类所在的依赖)
super.entity=com.demo.entity.superclass
# 设置超类包含的属性(生成实体类中会忽略这些定义的通用属性,因为在超类中已经存在)
super.entity.columns=id,create_by,create_date,update_by,update_date,delete_flag

# 是否添加@slf4j注解
log4j.open=true
# 是否开启lombok模式
lombok.model=true

# 是否有统一返回信息包装类; 
unified.results=true
# 包装类全路径;
unified.results.fullpath=
# 包装类类名;
unified.results.class=
# 包装类默认成功方法名;
unified.results.static.success.set=
# 包装类默认失败方法名;
unified.results.static.failure.set=
# 错误消息;
unified.results.static.failure.msg=
```

## 错误解决
#### 1. 默认Mybatis-plus提供单表方法提示invalid bound statement (not found)
> 查看是否自定义了SessionFactory.将自定义的切换为MybatisSqlSessionFactoryBean
```java
public SqlSessionFactory dataSourceFactory() throws Exception {
    MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource());
    return factoryBean.getObject();
}
```
