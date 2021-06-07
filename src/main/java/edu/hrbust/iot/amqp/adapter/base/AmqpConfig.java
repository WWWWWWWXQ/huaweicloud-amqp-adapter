package edu.hrbust.iot.amqp.adapter.base;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AMQP 配置类
 * 注入配置文件中给出的华为云 IoTDA 配置信息
 *
 * @author WWWWWWWXQ
 */
@Getter
@Component
public class AmqpConfig {

    // 连接凭证接入键值。
    @Value("${huawei.qpid.accessKey}")
    private String accessKey;

    // 连接凭证接入码。
    @Value("${huawei.qpid.accessCode}")
    private String password;

    // 按照qpid-jms的规范，组装连接URL。
    @Value("${huawei.qpid.broker-url}")
    private String connectionUrl;

    // 队列名，可以使用默认队列DefaultQueue
    @Value("${huawei.qpid.queueName}")
    private String queueName;

    // 主题名，可以使用默认队列DefaultTopic
    @Value("${huawei.qpid.topicName}")
    private String topicName;

}