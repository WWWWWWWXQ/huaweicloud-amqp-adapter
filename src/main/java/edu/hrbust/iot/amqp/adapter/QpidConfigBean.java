package edu.hrbust.iot.amqp.adapter;

import org.apache.qpid.jms.JmsTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

/**
 * 配合JmsTemplate使用，作为其配置类
 */
@Component
@EnableJms
public class QpidConfigBean {

    @Value("${huawei.qpid.topicName}")
    private String myTopic;

    @Bean
    public JmsTopic topic(){
        return new JmsTopic(myTopic);
    }
}
