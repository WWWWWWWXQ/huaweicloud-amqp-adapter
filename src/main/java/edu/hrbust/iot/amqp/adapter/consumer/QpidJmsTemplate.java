package edu.hrbust.iot.amqp.adapter.consumer;

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
 * Qpid 模版工具类
 * 封装核心类 AmqpAdapter
 *
 * @author WWWWWWWXQ
 */
@Component
public class QpidJmsTemplate {

    private static final long TIME_OUT = 5000;  // 超时时间，单位毫秒
    private final AmqpAdapter amqpAdapter;

    @Autowired
    public QpidJmsTemplate(AmqpAdapter amqpAdapter) {
        this.amqpAdapter = amqpAdapter;
    }

    /**
     * 接收消息，以原始数据返回
     *
     * @return List<<Message>Message</Message>>
     */
    public List<Message> receive(){
        return amqpAdapter.receiveMessageList(TIME_OUT);
    }

    /**
     * 接收消息，并转换为指定类型对象
     * 采用流式编程配合fastjson工具进行JSON格式转换
     *
     * @param targetClass 需要转换成的类
     * @return 转换之后的对象列表
     */
    public <T> List<T> receiveAndConvertToJson(Class<T> targetClass) {
        List<String> messages =  receiveAndConvertToString();
        if (messages.isEmpty()){
            return Collections.emptyList();
        }
        return messages.stream()
                .map(message -> JSON.parseObject(message, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * 将接收到的消息转换成String类型并返回
     *
     * @return List<<Message>String</Message>>
     */
    private List<String> receiveAndConvertToString(){
        List<Message> messages = receive();
        if (messages.isEmpty()){
            return Collections.emptyList();
        }
        return messages.stream()
                .map(this::receiveMessageBody)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String receiveMessageBody(Message message){
        String messageBody = null;
        try {
            messageBody =  message.getBody(String.class);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return messageBody;
    }

}
