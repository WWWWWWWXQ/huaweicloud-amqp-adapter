# huaweicloud-amqp-adapter

## 介绍

### huaweicloud-amqp-adapter是什么？

huaweicloud-amqp-adapter是一个遵循Apache Qpid JMS及其SDK开发的**AMQP Consumer**客户端工具，用于接入华为云IoTDA平台。

### huaweicloud-amqp-adapter有什么？

项目有两个主要分支——`master`和`adapter-master`

1. `master`分支为毕设整体项目结构，`v1.0.1`包含以下功能:
    - adpater——AMQP Consumer部分。
    - web——将Consumer接收到的数据处理并入库；响应前端查询请求，对数据库进行查询操作。
    - security——用户登录注册部分，采用Spring Security开发
2. `adapter-master`分支为抽离出来的AMQP Consumer部分，用于单独部署:
    - adpater——AMQP Consumer部分。
    - web——将Consumer接收到的数据处理并入库；根据需求向外暴露HTTP接口供查询。

## 目录结构

本项目的核心工具为单独抽离出来的`adapter-master`分支

```shell
src/main
|── java
|   |── edu.hrbust.iot.amqp
|   	|── HuaWeiCloudAmqpAdapterApplication.java  # SpringBoot 启动类
|       |── adapter                                 # AMQP Consumer部分
|       |   |── base                                ## 核心代码包
|       |   |   |── AmqpAdapter.java							  ### 核心代码类
|       |   |   |── AmqpConfig.java                 ### 华为云物联网平台的配置信息
|       |   |   |── QpidConnectionListener.java     ### Connection的监听类
|       |   |── consumer                            ## 存放QpidConsumer的继承实现类
|       |   |   |── QpidConsumer.java               ### Consumer模版类，约定继承
|       |   |   |── HealthConsumer.java             ### 消费者样例
|       |   |   |── QpidJmsTemplate.java            ### AmqpAdapter的封装工具类
|       |   |── entity                              ## 自定义消息属性类包
|       |       |── common                          ### 实体工具包
|       |       |   |── BaseMessageTemplate.java    #### 云平台消息JSON格式模版类，需继承
|       |       |   |── Properties.java							#### 云平台消息属性模板类，需继承
|       |       |   |── utils                       ### 云平台消息工具类
|       |       |       |── Body.java               #### 消息体，包含一个Services列表
|       |       |       |── Header.java             #### 消息头
|       |       |       |── NotifyData.java         #### 消息包，包含消息体和消息头
|       |       |       |── Services.java           #### 消息内容，包含Properties类及其子类
|       |       |── heart                           ### 自定义消息样例包
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
    |── application.properties                    # 配置文件

```



## 开发流程

按照项目目录结构，开发需分为`adapter`和`web`两部分进行

### adapter

#### 1. 配置信息

在华为云IoTDA平台上完成设备字段映射并完成所有配置之后，将配置信息填入`application.properties`配置文件

```properties
huawei.qpid.accessKey=
huawei.qpid.accessCode=
huawei.qpid.broker-url=amqps://${huawei.qpid.UUCID}.iot-amqps.cn-north-4.myhuaweicloud.com:5671?amqp.vhost=default&amqp.idleTimeout=8000&amqp.saslMechanisms=PLAIN
huawei.qpid.UUCID=
huawei.qpid.queueName=
```



#### 2. 创建实体类

在`amqp.adapter.entity`中新建一个实体包并创建以下三个类（e.g. health包）

> 1. 属性类 `extends Properties`
> 2. AMQP 消息接收类 `extends BaseMessageTemplate<P extends Properties>`
> 3. 消息转换工具类 `Converter`

云平台消息样例如下：

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
          // properties
        },
        "event_time": "20210521T122548Z"
      }]
    }
  }
}
```

华为云物联网平台会将设备上报的数据流按照配置好的字段分装成JSON格式传送。将JSON格式转换成实体类的方案有很多种，流行的有：`Alibaba fastjson`和`FastXML jackson`。这两个工具分析JSON数据时都需要指定一个目标实体类，将JSON格式中的数据复制给对应的实体属性。

鉴于JSON中只有`properties`字段是自定义的，因此只需规范此字段。开发自定义实体类时，只需继承`adapter.entity.common`包中已将冗余信息解耦的`BaseMessageTemplate`类并定义相应的`Properties`类传入即可。

``` java
// 属性类
public class HealthProperties extends Properties {
    // properties
}

// AMQP 消息接收类
public class HealthAmqpMsg extends BaseMessageTemplate<HealthProperties> {

}

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



#### 3. 创建消费者

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








