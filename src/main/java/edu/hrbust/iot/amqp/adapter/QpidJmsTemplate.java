package edu.hrbust.iot.amqp.adapter;

import com.alibaba.fastjson.JSON;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.transports.TransportOptions;
import org.apache.qpid.jms.transports.TransportSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


import javax.jms.*;

/**
 * 使用JmsTemplate进行消息接收
 * JmsTemplate 在 AmqpConfig中配置生成
 */
@Component
public class QpidJmsTemplate {

    @Autowired
    private AmqpConfig amqpConfig;

    @Autowired
    private JmsTemplate jmsTemplate;

    public Message testReceive() throws Exception {

//        //连接凭证接入键值。
//        //UserName组装方法，请参见文档：AMQP客户端接入说明。
//        //时间戳
//        long timeStamp = System.currentTimeMillis();
//        String userName = "accessKey=" + amqpConfig.getAccessKey() + "|timestamp=" + timeStamp;
//
//        JmsConnectionFactory factory = new JmsConnectionFactory(userName, amqpConfig.getPassword(), amqpConfig.getConnectionUrl());
//
//        //信任服务端
//        TransportOptions to = new TransportOptions();
//        to.setTrustAll(true);
//        factory.setSslContext(TransportSupport.createJdkSslContext(to));
//
//        jmsTemplate.setConnectionFactory(factory);

        String destinationName = amqpConfig.getTopicName();
        return doReceive(destinationName);
    }

    private Message doReceive(String destinationName){
        return jmsTemplate.receive(destinationName);
    }

    public <T> T receiveJsonAndConvert(String destinationName, Class<T> targetClass) throws JMSException {
        Message message = doReceive(destinationName);
        return JSON.parseObject(message.getBody(String.class), targetClass);
    }

}
