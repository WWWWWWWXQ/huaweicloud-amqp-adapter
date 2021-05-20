package edu.hrbust.iot.amqp.adapter;

import com.alibaba.fastjson.JSON;
import edu.hrbust.iot.amqp.adapter.base.AmqpAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.jms.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 *
 */
@Component
public class QpidJmsTemplate {

    private static final long TIME_OUT = 5000;

    @Autowired
    private AmqpAdapter amqpAdapter;

    public List<Message> receive(){
        try {
            return amqpAdapter.receiveMessageList(TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<String> receiveAndConvertToString(){
        List<Message> messages = receive();
        if (messages.isEmpty()){
            return Collections.emptyList();
        }
        return messages.stream().map(this::receiveMessageBody).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public String receiveMessageBody(Message message){
        String messageBody = null;
        try {
            messageBody =  message.getBody(String.class);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return messageBody;
    }

    public <T> List<T> receiveAndConvertToJson( Class<T> targetClass) {
        List<String> messages =  receiveAndConvertToString();
        if (messages.isEmpty()){
            return Collections.emptyList();
        }
//        System.out.println("接收到消息:");
//        messages.forEach(System.out::println);
        return messages.stream().map(message -> JSON.parseObject(message, targetClass)).collect(Collectors.toList());
    }

}
