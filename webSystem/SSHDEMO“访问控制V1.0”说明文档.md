
#SSHDEMO“访问控制V1.0”说明文档#
|   Version |   Author           |   Date    |   P.S.    |
| --------- |:------------------:|:---------:| ---------:|
| 1.0.0(pre)|    陈斌             | 20160407  |  创建接口文档     |
| 1.0.1(pre)|    陈斌             | 20160430  |  第一版文档详细说明 |

===

#第一章 数据库说明#
## Tables

| Name | Description	| Remarks |
| --- | ---  | --- |
|access_control | 访问控制表 | 无 |
|strategy | 策略表 | 无 |
|access_control_strategy | 访问控制表和策略表的关联维护表| 无 |

###access_control

| Field | Type | Description | Remarks |
| ----- | ---- | ----------- | ------- |
| access_control_id | int(11) | 访问控制ID | primary key, index field |
| group_id | int(11) | 组ID | |
| path | varchar(225) | 文件/目录ID |本来存完整路劲的，鉴于目前其他模块都用ID，此处也存ID |

###strategy

| Field | Type | Description | Remarks |
| ----- | ---- | ----------- | ------- |
| strategy_id | int(11) | 策略ID | primary key, index field |
| allow_create_floder | int(11) | 是否允许创建目录 | 允许为1，不允许为0 |
| allow_delete_floder | int(11) | 是否允许删除目录 | 允许为1，不允许为0 |
| allow_share_floder  | int(11) | 是否允许分享目录 | 允许为1，不允许为0 |
| allow_upload_file   | int(11) | 是否允许上传文件 | 允许为1，不允许为0 |
| allow_download_file | int(11) | 是否允许下载文件 | 允许为1，不允许为0 |
| allow_delete_file   | int(11) | 是否允许删除文件 | 允许为1，不允许为0 |
| integrity           | int(11) | 是否需要完整性校验|需要为1，不需要为0 |
| operate_ways        | int(11) | 对文件操作方式   |通用方式为0，ABE加密方式为1|
| property_expression | varchar(255) | 属性表达式 |定义和规则见具体说明|

###access_control_strategy

| Field | Type | Description | Remarks |
| ----- | ---- | ----------- | ------- |
| access_control_id | int(11) | 访问控制ID | primary key, index field |
| strategy_id | int(11) | 策略ID | primary key |



+ 该表维持访问控制表和策略表多对多的关联
+ access_control_id关联访问控制表的access_control_id
+ strategy_id关联策略表的strategy_id

===

#第二章 接口说明#
##1、状态码定义
###（1）返回状态码定义：

```
1、"20000"：本次请求成功
2、"40000"：本次请求失败
```

###（2）错误状态码定义

```
1、"40001":"用户不存在"
2、"40002":"请求参数错误"
3、"40003":"数据库操作失败"
4、"40004":"属性表达式解析失败"
```
##2、接口定义

### /queryaccess

+ 描述 ：根据用户属性查询该用户对某目录/文件是否拥有某权限

+ method `GET/POST`

+ request

```
{
	"userId" : "12345434",           // 用户ID
	"groupId" : "12097332",          // 组ID
	"fileFolderId" : "1213432f",     // 文件/目录ID（文件：文件本身的ID后加f，目录：目录本身的ID后加d）
	"privilege" : " allowCreateFloder
	               |allowShareFloder
	               |allowDeleteFloder
	               |allowUploadFile
	               |allowDownloadFile
	               |allowDeleteFile"   //六种权限任取一种
}  
}
```

+ response

success

```
{
    "code":{
        "20000":"ok"
    },
    "ret":{
        "allowShareFloder":"1"
    }
}
```

failed

```
{
    "code":{
        "40000":"error"
    },
    "ret":{
        "40001":"用户不存在"
    }
}
```

### /queryaccessall

+ 描述 ：根据用户属性查询该用户对某目录/文件的全部权限

+ method `GET/POST`

+ request

```
{
	"userId" : "12345434",           // 用户ID
	"groupId" : "12097332",          // 组ID
	"fileFolderId" : "1213432f"     // 文件/目录ID（文件：文件本身的ID后加f，目录：目录本身的ID后加d）
}
```

+ response

success

```
{
    "code":{
        "20000":"ok"
    }
    "ret":{
        "allowCreateFloder":"0",
        "allowShareFloder":"1",
        "allowDownloadFile":"1",
        "allowUploadFile":"1",
        "allowDeleteFile":"1",
        "allowDeleteFloder":"0"
    }
}
```

failed

```
{
    "code":{
        "40000":"error"
    },
    "ret":{
        "40001":"用户不存在"
    }
}
```
### /querypolicy

+ 描述 ：根据用户的ID，查询与该用户相关的目录/文件对应的属性表达式

+ 申明：该接口只允许管理员调用，接口涉及到获取策略中的属性表达式，属绝密性质的接口，调用前请做判断）

+ method `GET/POST`

+ request

```
{
	"userId" : "12345434",           // 用户ID
	"groupId" : "12097332",          // 组ID
	"fileFolderId" : "1213432f"      // 文件/目录ID（文件：文件本身的ID后加f，目录：目录本身的ID后加d）
}
```
+ response

success

```
{
    "code":{
        "20000":"ok"
    }
    "ret":{
        "7":"#username='a'&(password='12' $ ro le='2')&!(userID = 'fd')#"  //键为策略ID，值为属性表达式
        ......
    }
}
```

+ failed

```
{
    "code":{
        "40000":"error"
    },
    "ret":{
        "40001":"用户不存在"
    }
}
```
### /insertpolicy

+ 描述 ：向数据库增加/更新一条策略

+ method `POST`

+ request

```
{
	"userId" : "12345434",           // 用户ID
	"groupId" : "12097332",          // 组ID
	"fileFolderId" : "1213432f"      // 文件/目录ID（文件：文件本身的ID后加f，目录：目录本身的ID后加d）
	"allowCreateFloder":"0",
	"allowShareFloder":"1",
	"allowDeleteFloder":"0",
	"allowUploadFile":"1",
	"allowDownloadFile":"0",
	"allowDeleteFile":"1",
	"operateWays":"1",
	"integrity":"1",
	"propertyExpression":"#username=a&(password=12 $ ty pe=2)$!userID = '1'#"
}
```
+ response

success

```
{
    "code":{
        "20000":"ok"
    }，
    "ret":{
        "20000":"插入/更新成功"
    },    
}
```

+ failed

```
{
    "code":{
        "40000":"error"
    },
    "ret":{
        "40001":"用户不存在"
    }
}
```
### /deletepolicy

+ 描述 ：根据策略ID删除一条策略

+ method `POST`

+ request

```
{
    "policyId":"10"
}
```
+ response

success

```
{
    "code":{
        "20000":"ok"
    },
    "ret":{
        "20000":"数据库操作成功，策略已删除"
    }
}
```

+ failed

```
{
    "code":{
        "40000":"error"
    },
    "ret":{
        "40002":"请求参数错误，该策略不存在"
    }
}
```
### /conflictdetection

+ 描述 ：策略冲突检测

+ method `POST`

+ request

```
{
    "groupId":"123",
    "fileFolderId":"24325435435344f",
	"propertyExpression":"#username=\'a\'#",
	"allowCreateFloder": 1	,
	"allowShareFloder":0,
	"allowDeleteFloder":1,
	"allowUploadFile":0,
	"allowDownloadFile":1,
	"allowDeleteFile":1
}
```
+ response

success

```
{
    "1":[                                //数字1表示与策略ID为1的策略冲突了，后面列出了哪些权限冲突
        "allowCreateFloder",
        "allowShareFloder",
        "allowDeleteFloder",
        "allowUploadFile",
        "allowDownloadFile"
    ]
}
```

+ failed

```
{
    "code":{
        "40000":"error"
    },
    "ret":{
        "40004":"属性表达式解析错误"
    }
}
```


#第三章 源码修改说面

##1、文档中包含的符号说明 

``` 
    【A】--增加的文件或功能
    【D】--删除的文件或功能
    【U】--更新（修改）的文件或功能

```

##2、源代码说明

### JS文件
```
    【A】accesscontrol.js （webapp/resources/js/）
```

### View层 （webapp/jsp/accesscontrol）：

```
    【A】index.jsp 文件(接口测试文件，上线前请删除)
```

### Controller层 （pku.yang.controller）：

```
    【A】AccessControlHandler.java 文件
```
    
### Service接口层 （pku.yang.service）
   
```
    【A】IAccessControlService.java文件
```

###Service实现层 （pku.yang.service.imp）
   
```
    【A】AccessControlService.java文件
    【A】
```
   
### Dao接口层 （pku.yang.dao）
   
```
    【A】IAccessControlDao.java文件
    【A】IAccessControlRepository.java文件
    【A】IStrategyRepository.java文件
```
### Dao实现层 （pku.yang.dao）
```
    【A】AccessControlDao.java文件
```
### Model层 （pku.yang.model）
   
```
    【A】AccessControl.java文件
    【A】Strategy.java文件
    【A】AccessControlParams.java文件
```
### Tool （plu.yang.tool）
```
    【A】AttrExpressToSql.java文件
```
##3、配置文件说明 

###config.properties

```
【A】jdbc_url:useSSL=false
```

### spring-mvc.xml   

```
【A】处理静态资源的声明式配置
    <mvc:default-servlet-handler/>
```
###spring.xml

```
 1、【U】<beans> 的xsd升级到4.2，   
```
```
 2、【A】引入了了jpa（基于hibernate的抽象层），cache（缓存机制），tx（事物机制）（beans属性中）
```
```    
 3、【A】数据源的配置
        <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		    <property name="user" value="${jdbc_username}"></property>
		    <property name="password" value="${jdbc_password}"></property>
		    <property name="driverClass" value="${driverClassName}"></property>
		    <property name="jdbcUrl" value="${jdbc_url}"></property>
	    </bean>
```
```	    
4、【A】JPA的 EntityManagerFactory 配置
	    <bean id="entityManagerFactory"
		    class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		    <property name="dataSource" ref="dataSource"></property>
		    <property name="jpaVendorAdapter">
			    <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
		    </property>	
		    <property name="packagesToScan" value="pku.yang.model"></property>
		    <property name="jpaProperties">
			    <props>
				    <prop key="hibernate.ejb.naming_strategy">
				        org.hibernate.cfg.ImprovedNamingStrategy
			        </prop>
				    <prop key="hibernate.hbm2ddl.auto">update</prop>
				    <prop key="hibernate.show_sql">true</prop>
				    <prop key="hibernate.format_sql">true</prop>
				    <prop key="hibernate.dialect">
				        org.hibernate.dialect.MySQL5InnoDBDialect
				    </prop>
				    <prop key="hibernate.cache.use_second_level_cache">true</prop>
				    <prop key="hibernate.cache.region.factory_class">
				        org.hibernate.cache.ehcache.EhCacheRegionFactory
				    </prop>
				    <prop key="hibernate.cache.use_query_cache">true</prop>
			    </props>
		    </property>
		    <property name="sharedCacheMode" value="ENABLE_SELECTIVE"></property>
	    </bean>
```
```
5、【A】配置事务
	    <bean id="transactionManager"
	        class="org.springframework.orm.jpa.JpaTransactionManager">
		    <property name="entityManagerFactory" ref="entityManagerFactory"></property>
	    </bean>
```
```	
6、【A】配置支持基于注解的事务
	    <tx:annotation-driven transaction-manager="transactionManager"/>
```
```	    
7、【A】配置 SpringData
	    <jpa:repositories base-package="pku.yang.dao" 
	        entity-manager-factory-ref="entityManagerFactory">
	    </jpa:repositories>
```

### pom.xml

```

    1、【A】默认编码方式（UTF-8）
	    <properties>  
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  
        </properties>
```
```
        
    2、【A】将Maven仓库地址修改为国内“开源中国（OsChina）”的地址（原始Maven仓库在国外，访问速度受限）
        <repositories>
            <repository>
                <id>osChina</id>
                <name>osChina repository</name>
                <url>http://maven.oschina.net/content/groups/public/</url>
            </repository>
        </repositories>
```
```
        
    3、【A】增加了适应不同版本JDK的配置（此处设置为JDK1.7）
        <build>
             <finalName>sshdemo Maven Webapp</finalName>
             <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>1.7</source>
                        <target>1.7</target>
                    </configuration>
                </plugin>
             </plugins>
	    </build>
```

```

    4、【A】C3p0二级缓存（降低大量访问控制给数据库带来的压力）
        <dependency>
          <groupId>com.mchange</groupId>
          <artifactId>c3p0</artifactId>
          <version>0.9.2.1</version>
        </dependency>
    
         <dependency>
          <groupId>com.mchange</groupId>
          <artifactId>mchange-commons-java</artifactId>
          <version>0.2.3.4</version>
        </dependency>
    
        <dependency>
          <groupId>org.hibernate</groupId>
          <artifactId>hibernate-c3p0</artifactId>
          <version>5.0.6.Final</version>
        </dependency>
```
```

    7、【A】JSON支持
        <dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.1.5</version>
	    </dependency>
	    <dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-core</artifactId>
		    <version>2.1.5</version>
	    </dependency>
	    <dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-annotations</artifactId>
		    <version>2.1.5</version>
	    </dependency>        
```

```
    8、【A】JPA和SpringData持久化的支持
    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-jpa</artifactId>
      <version>1.8.2.RELEASE</version>
    </dependency>
```

===

#第四章 属性表达式的书写规范
##1、简介
###属性表达式包含的符号种类
```
起始符："#"，
逻辑运算符："与(&)"、"或($)"、"非(!)",
数值表达式："大于等于(>=)"、"小于等于(<=)"、"等于(=)"、"不等于(<>)",
括号： "左圆括号("、"右圆括号)"
终结符："#"
```
##2、符号优先级说明
|           |     $    |     &     |     !     |     (     |     )     |     #     |
| --------- |:--------:|:---------:|:---------:| ---------:|:---------:| ---------:|
|     $     |     >    |     <     |     <     |     <     |     >     |     >     |
|     &     |     >    |     >     |     <     |     <     |     >     |     >     |
|     !     |     >    |     >     |     >     |     <     |     >     |     >     |
|     (     |     <    |     <     |     <     |     <     |     =     ||
|     )     |     >    |     >     |     >     |           |     >     |     >     |
|     #     |     <    |     <     |     <     |     <     |           |     =     |

##3、属性的范围（区分大小写）

###user属性
 
 ```
 user_pid ：用户自身唯一标识符
 userID：对应是Student的ID，或者是Teacher的ID
 username：用户名
 password：密码
 storageID：存储ID
 type：类型（老师或者学生）
 roleNum：角色
 ```
###学生属性
```
id：学生ID
name：学生姓名
department
courses
teacherID
studygroup
academy
age
```
###老师属性
```
id：老师ID
name：老师姓名
department
title
duty
studyGroup
courses
age
```
##4、属性表达式的书写规则
 ```
 1、属性名称必须用上面规定的属性名称，且大小写敏感
 2、属性值需要加上英文单引号，无论是字符型还是数字型，如username='chenbin'&password='123456'
 3、属性表达式允许有空格，可以连续一个或者多个空格
 4、括号与括号之间必须有逻辑运算符
 5、每个表达式必须以"#"开始，并且以"#"结束
 6、不等号一律写成"<>"，切记不要写成"!="
 7、属性表达式不得超过255个字符
 8、属性表达式示例：#username='chenbin'&(password='12' $ ty pe='1')$!userID = '1'#
 
 
 ```

===

 
#第五章 总结

```
基于属性的访问控制是个难点，目前基本功能已实现。
```





	
