
![img](https://gitee.com/WWWWWWWXQ/images/raw/master/Qpid/huaweicloud_logo.jpg)

![img](https://gitee.com/WWWWWWWXQ/images/raw/master/Qpid/Apache_Qpid.jpg)

## 1. Intro

### Huaweicloud-amqp-adapter是什么?

`Huaweicloud-amqp-adapter`是一个遵循[Apache Qpid JMS](http://qpid.apache.org/components/jms/index.html)及其SDK和[AMQP协议](https://www.amqp.org/?spm=a2c4g.11186623.2.16.4954719fdfh8Qf)开发的**AMQP Consumer**客户端工具，用于接入`华为云IoTDA平台`并接收平台传输的数据。


### Huaweicloud-amqp-adapter有什么？

项目有两个主要分支——`master`和`adapter-master`:

1. `adapter-master` 分支为AMQP Consumer部分。项目主分支，用于单独部署:
   - `adpater`——AMQP Consumer部分
   - `web`——将Consumer接收到的数据处理并入库；根据需求向外暴露HTTP接口供查询
2. `master` 分支为毕设项目。项目副分支，`[iot-system-v1.0.0]` 包含以下功能:
   - `adpater`——AMQP Consumer部分
   - `web`——将Consumer接收到的数据处理并入库；响应前端查询请求，对数据库进行查询操作
   - `security`——用户登录注册部分，采用 `Spring Security` 开发

---

## 2. 目录结构

```shell
src/main
|── java
|   |── edu.hrbust.iot.amqp
|       |── HuaWeiCloudAmqpAdapterApplication.java  # SpringBoot 启动类
|       |── adapter                                 # AMQP Consumer部分
|       |   |── base                                ## 核心代码包
|       |   |   |── AmqpAdapter.java                ### 核心代码类
|       |   |   |── AmqpConfig.java                 ### 华为云物联网平台的配置信息
|       |   |   |── QpidConnectionListener.java     ### Connection的监听类
|       |   |── consumer                            ## 存放消费者类
|       |   |   |── QpidConsumer.java               ### Consumer模版类，可自主选择是否继承
|       |   |   |── HealthConsumer.java             ### 消费者样例
|       |   |   |── QpidJmsTemplate.java            ### AmqpAdapter的封装核心工具类
|       |   |── entity                              ## 存放自定义消息属性类
|       |       |── common                          ### 存放工具类
|       |       |   |── BaseMessageTemplate.java    #### 云平台消息JSON格式转换模版类，需继承
|       |       |   |── Properties.java             #### 云平台消息属性模板类，需继承
|       |       |   |── utils                       ### 存放云平台消息工具类
|       |       |       |── Body.java               #### 消息体，包含一个Services列表
|       |       |       |── Header.java             #### 消息头
|       |       |       |── NotifyData.java         #### 消息包，包含消息体和消息头
|       |       |       |── Services.java           #### 消息内容，包含Properties类及其子类
|       |       |── heart                           ### 存放自定义消息样例包
|       |           |── HealthAmqpMsg.java          #### 继承BaseMessageTemplate<HealthProperties>
|       |           |── HealthAmqpMsgConverter.java #### 将AMQP消息转换为Service可以处理的DTO
|       |           |── HealthProperties.java       #### 继承Properties并定义需要的属性
|       |── web                                     # Web后端部分
|            |── controller                         ## Controller控制层，响应HTTP请求
|            |── dao                                ## DAO持久化层，与DB交互
|            |── entity                             ## 实体包
|            |   |── heart                          ### 样例包，存放PO、DTO和VO
|            |── service                            ## Service逻辑层，定义接口
|            |   |── impl                           ### 实现类包，实现在Service层定义的接口
|            |── utils                              ## 工具包
|                 |── common                        ### 基础工具类
|                 |── converter                     ### 三层架构中各层间数据转换工具
|── resources                                     
    |── application.properties                      # 配置文件

```

---

## 3. 开发流程

按照项目目录结构，开发需分为`adapter`和`web`两部分进行

- `adapter`
   1. 填写云平台配置信息
   2. 创建用于接收消息的实体类和属性转换工具类
   3. 编写AMQP Consumer与云平台建立连接并接收数据，调用`web`入库操作
- `web`
   1. 编写与`adapter`部分消息实体类属性对应的POJO（VO、DTO和PO）
   2. 编写`Vo<->DTO`和`DTO<->PO`两个`converter`
   3. 编写Controller、Service和DAO层的实现代码



### 3.1 adapter

#### 3.1.1 配置信息

在[华为云IoTDA平台](https://support.huaweicloud.com/devg-iothub/iot_01_00100_2.html)上完成设备字段映射和接入配置之后，将配置信息填入`application.properties`配置文件

```properties
huawei.qpid.accessKey=
huawei.qpid.accessCode=
huawei.qpid.broker-url=amqps://${huawei.qpid.UUCID}.iot-amqps.cn-north-4.myhuaweicloud.com:5671?amqp.vhost=default&amqp.idleTimeout=8000&amqp.saslMechanisms=PLAIN
huawei.qpid.UUCID=
huawei.qpid.queueName=
```



#### 3.1.2 创建实体类

华为云物联网平台会将设备上报的数据流按照配置好的字段映射封装成JSON格式传输，云平台消息样例如下：

```json
{
  "resource": "device.property",
  "event": "report",
  "event_time": "20210521T122548Z",
  "notify_data": {
    "header": {
      "app_id": "e6c196d40df84846bacbd9573dabb685",
      "device_id": "5fd6df9937f2a30303b55693_863434047705329",
      "node_id": "863434047705329",
      "product_id": "5fd6df9937f2a30303b55693",
      "gateway_id": "5fd6df9937f2a30303b55693_863434047705329"
    },
    "body": {
      "services": [{
        "service_id": "HeartRateDeviceService",
        "properties": {
          "properties":"properties",
          ...
        },
        "event_time": "20210521T122548Z"
      }]
    }
  }
}
```

将JSON格式转换成实体类的方案有很多种，流行的有[Alibaba fastjson](https://gitee.com/wenshao/fastjson?_from=gitee_search)和[FastXML jackson](https://github.com/FasterXML/jackson)，两者在分析JSON数据时都需要指定一个目标实体类，才能将JSON数据按照字段名一一复制给对应的实体属性。

鉴于`华为云IoTDA平台`规定的JSON数据中只有`properties`字段是需要自定义的，因此只需规范此字段。

> 在`adapter.entity`包下新建一个实体包并创建以下三个类（e.g. `health`包）
>
> 1. 属性类 `extends Properties`
> 2. AMQP 消息接收类 `extends BaseMessageTemplate<P extends Properties>`
> 3. 消息转换工具类

开发自定义实体类时，只需继承`adapter.entity.common`包中已将冗余信息解耦的`BaseMessageTemplate`类并定义相应的`Properties`类传入即可。

``` java
// 属性类
public class HealthProperties extends Properties {
    // properties
    ...
}

// AMQP 消息接收类
public class HealthAmqpMsg extends BaseMessageTemplate<HealthProperties> {}

// 消息转换工具类
public class HealthAmqpMsgConverter {
    public HealthDataDTO convertToDTO(HealthAmqpMsg healthData){
        HealthDataDTO dto = new HealthDataDTO();
        // build header
        Header header = healthData.getNotifyData().getHeader();
        dto.setAppId(header.getAppId());
        dto.setDeviceId(header.getDeviceId());
        dto.setNodeId(header.getNodeId());
        dto.setProductId(header.getProductId());
        dto.setGatewayId(header.getGatewayId());

        // build body
        Body<HealthProperties> body = healthData.getNotifyData().getBody();
        Services<HealthProperties> services =  body.getServices().get(0);
        dto.setServiceId(services.getServiceId());
        HealthProperties healthProperties = services.getProperties();

        // build properties
        
        return dto;
    }
}
```



#### 3.1.3 创建消费者

`adapter.consumer`包中已定义了一个消费者抽象模板类`QpidConsumer`，并且自动注入了工具类`QpidJmsTemplate`。可以自主选择继承`QpidConsumer`类或直接使用`QpidJmsTemplate`工具类进行消息接收。

```java
// 开发消费者
public class HealthConsumer extends QpidConsumer {

    @Autowired
    private HealthService heartHealthService;

    @Autowired
    private HealthAmqpMsgConverter heartConverter;

    private void save(HealthAmqpMsg healthData){
        HealthDataDTO healthDataDTO = heartConverter.convertToDTO(healthData);
        heartHealthService.save(healthDataDTO);
    }

    @Scheduled(fixedDelay = 300000)
    public void receiveHealthData() {
        log.info("[定时任务开始], {}", LocalDateTime.now());
        List<HealthAmqpMsg> healthData = qpidJmsTemplate.receiveAndConvertToJson(HealthAmqpMsg.class);
        log.info("共接收到 [{}] 条消息", healthData.size());

        if (!healthData.isEmpty()) {
            log.info("[准备入库], {}, 入库信息为: {}",LocalDateTime.now(), healthData);
            healthData.forEach(this::save);
            log.info("[已入库，定时任务结束], {} ", LocalDateTime.now());
        }
    }
}
```



### 3.2 web

#### 3.2.1 编写POJO

##### 「PO」Persistant Object 持久层对象

```java
@Data   //lombok注释，用于自动生成getter和setter
@Entity //javax.persistence注释，用于注释PO表明其为数据库实体类
// 类名需和表名对应，此类对应health_data表
public class HealthData extends BasePO { 
    // header
    private String appId;
    private String deviceId;
    private String nodeId;
    private String productId;
    private String gatewayId;

    // body.services
    private String serviceId;
    private Date eventTime;
    
    // properties
    ...
}

```

##### 「DTO」Data Transfer Object 数据传输对象

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
// 用于在DAO和AMQP Consumer与Service层间传递消息
public class HealthDataDTO extends BaseDTO { 
    // header
    private String appId;
    private String deviceId;
    private String nodeId;
    private String productId;
    private String gatewayId;

    // body.services
    private String serviceId;
    private Date eventTime;
  
    // properties
    ...
}

```

##### 「VO」Value Object 值对象

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
// 响应HTTP请求时返回的数据
public class HealthDataVO implements Serializable { 
    // 前端需要展示的数据字段
    ...
}
```



#### 3.2.2 编写转换工具类

`VO<->DTO`和`DTO<->PO`间复制数据需要两个转换工具。

本项目通过 `java.reflect` 提供的反射工具和`Spring`提供的 `BeanUtils `工具进行类型转换时的属性复制，并封装在 `BaseConverter` 接口中。

编写工具类的时候只需实现上述接口并指定 `源类SOURCE` 和 `目标类TARGET` 即可:

##### HealthDVConverter

```java
//Vo<->DTO
public class HealthDVConverter 
  implements BaseConverter<HealthDataDTO, HealthDataVO> {}
```

##### HealthPDConverter

```java
//DTO<->PO
public class HealthPDConverter 
  implements BaseConverter<HealthData, HealthDataDTO> {}
```



#### 3.2.3 编写三层架构

##### Controller

```java
@CrossOrigin
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @Autowired
    private HealthDVConverter converter;
		
  	// 监听/health/queryPage的HTTP请求
    // Page为分页信息类，WebResponse为统一返回类
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public WebResponse<Page<HealthDataVO>> queryPage(@RequestBody(required = false) AmqpQuery amqpQuery){
        PageDTO<HealthDataDTO> pageDTO = healthService.queryPage(amqpQuery);
        return WebResponse.success(Page.from(pageDTO, dto ->converter.toTarget(dto)));
    }
}
```

##### Service

```java
// 面向接口开发，定义规范
package edu.hrbust.iot.amqp.web.service
public interface HealthService {

    PageDTO<HealthDataDTO> queryPage(AmqpQuery amqpQuery);

    void save(HealthDataDTO healthDataDTO);

    List<HealthDataDTO> queryAll();
}

// 接口实现类
package edu.hrbust.iot.amqp.web.service.impl
public class DefaultHealthService implements HealthService {
    
    @Autowired
    private HealthDataRepository repository;

    @Autowired
    private HealthPDConverter converter;
  
    //编写具体的实现代码
    @Override
    public PageDTO<HealthDataDTO> queryPage(AmqpQuery amqpQuery){}

  	@Override
    public void save(HealthDataDTO healthDataDTO){}
  
    @Override
    public List<HealthDataDTO> queryAll(){}
  
}
```

##### Dao

```java
// Dao层采用Spring Data JPA实现，只需继承JpaRepository即可实现简单的数据库查找功能
// JpaSpecificationExecutor接口提供分页功能
public interface HealthDataRepository extends
        JpaRepository<HealthData, Long>, JpaSpecificationExecutor<HealthData> {}
```



### 3.3 数据库

#### 3.3.1 实现

```sql
create table health_data
(
    id              int auto_increment, 
    uid             varchar(255) not null,
    service_id      varchar(255) null,
    max_heart_rate  int          not null,
    min_heart_rate  int          not null,
    aver_heart_rate int          not null,
    start_time      varchar(255) not null,
    end_time        varchar(255) not null,
    start_date      varchar(255) null,
    end_date        varchar(255) null,
    event_time      datetime     null,
    created_time    datetime     null,
    app_id          varchar(255) null,
    node_id         varchar(255) null,
    product_id      varchar(255) null,
    gateway_id      varchar(255) null,
    device_id       varchar(255) null,
    primary key(id),
);
```


