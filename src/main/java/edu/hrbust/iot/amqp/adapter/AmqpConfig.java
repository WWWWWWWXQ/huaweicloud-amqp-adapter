package edu.hrbust.iot.amqp.adapter;


import lombok.Getter;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.transports.TransportOptions;
import org.apache.qpid.jms.transports.TransportSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;


@Getter
@Component
public class AmqpConfig {
    //连接凭证接入键值。
    @Value("accessKey=${huawei.qpid.accessKey}")
    private String accessKey;

    //连接凭证接入码。
    @Value("${huawei.qpid.accessCode}")
    private String password ;

    //按照qpid-jms的规范，组装连接URL。
    @Value("${huawei.qpid.broker-url}")
    private String connectionUrl;

    //队列名，可以使用默认队列DefaultQueue
    @Value("${huawei.qpid.topicName}")
    private String topicName;

//    @Bean
//    ConnectionFactory createJmsConnectionFactory(){
//        //连接凭证接入键值。
//        //UserName组装方法，请参见文档：AMQP客户端接入说明。
//        //时间戳
//        long timeStamp = System.currentTimeMillis();
//        String userName = "accessKey=" + accessKey + "|timestamp=" + timeStamp;
//        JmsConnectionFactory factory = new JmsConnectionFactory(userName, password, connectionUrl);
//
//        //信任服务端
//        TransportOptions to = new TransportOptions();
//        to.setTrustAll(true);
//
//        try {
//            factory.setSslContext(TransportSupport.createJdkSslContext(to));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return factory;
//    }

    @Bean
    JmsTemplate createJmsTemplate() {
        //连接凭证接入键值。
        //UserName组装方法，请参见文档：AMQP客户端接入说明。
        //时间戳
        long timeStamp = System.currentTimeMillis();
        String userName = "accessKey=" + accessKey + "|timestamp=" + timeStamp;
        JmsConnectionFactory factory = new JmsConnectionFactory(userName, password, connectionUrl);

        //信任服务端
        TransportOptions to = new TransportOptions();
        to.setTrustAll(true);
        try {
            factory.setSslContext(TransportSupport.createJdkSslContext(to));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JmsTemplate(factory);
    }

}
