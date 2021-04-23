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

## 业务模块配置修改
1. Application启动类上添加注解`@MapperScan(basePackages={"mapperpath","mapperpath"})`
2. 配置讲解.必要加*
> 更多请查看[Mybatis-Plus使用配置](https://mp.baomidou.com/config/#%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE)
```yaml
#mybatis
mybatis-plus:
  # * 定义Mapper
  mapper-locations: classpath:com/reuben/demo/mapper/*Mapper.xml
  # * 实体类扫描，多个package用逗号或者分号分隔开。指定后mapper中可以直接使用类名不需要指定全限定类名(建议还是用全限定类名好)
  type-aliases-package: com.reuben.demo.entity
  # 搭配type-aliases-package使用,配置该属性后将只扫描该属性的子类作为域对象
  type-aliases-supertype:
  # 配置该属性后,sqlsessionfactorybean会去加载该包下的typehandler类(自定义类型转换类)
  type-handlers-package:
  # 定义枚举类包路径,如果配置该属性会帮实体类注入枚举字段
  type-enums-package:
  # mybatis-plus全局配置策略
  global-config:
    # 控制台打印Mybatis-plus logo开关
    banner: false
    # 可指定自定义SQL注入器`com.baomidou.mybatisplus.core.injector.ISqlInjector`来修改指定方法的执行sql(也可通过@bean注入)
    sql-injector:
    # 设置mapper超类,sqlInjector只会对该类的子类方法有影响
    super-mapper-class:
    # db生成策略
    db-config:
      # * 主键类型：0 AUTO 数据库ID自增，1 NONE 该类型为未设置主键类型约等于用户输入ID，2 INPUT 用户输入ID，3 ASSIGN_ID=ID_WORKER 全局唯一ID,雪花算法实现 4 ASSIGN_UUID 全局UUID String类型
      id-type: ASSIGN_ID
      #* 驼峰下划线
      table-underline: true
      column-underline: true
      # * 逻辑删除配置
      # entity逻辑删除字段属性名   实体类字段上加上@TableLogic注解
      logic-delete-field: deleteFlag
      #逻辑删除配置(1表示已删除)
      logic-delete-value: -1
      #逻辑未删除配置(0表示未删除)
      logic-not-delete-value: 0
  # * 配置mybatis原生支持
  configuration:
    # 开启驼峰命名映射
    map-underscore-to-camel-case: true
    # 默认枚举处理类,定义该属性后会由它处理枚举属性
    default-enum-type-handler:
    # 一级缓存域定义: 默认为session; 可设置为STATEMENT来关闭一级缓存（建议关闭,微服务分布式架构多节点情况下一级缓存也可能会有脏数据问题.查询效率应从数据库层面解决)
    local-cache-scope: STATEMENT
    # 是否开启二级缓存(建议关闭,对自定义多表查询有脏语句风险)
    cache-enabled: false
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
