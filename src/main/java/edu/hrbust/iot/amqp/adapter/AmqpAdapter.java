package edu.hrbust.iot.amqp.adapter;

import edu.hrbust.iot.amqp.adapter.listener.QpidConnectionListener;
import edu.hrbust.iot.amqp.web.dao.AmqpAdapterRepository;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.utils.converter.AmqpConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.transports.TransportOptions;
import org.apache.qpid.jms.transports.TransportSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;


/**
 * 原生代码
 */
@Slf4j
@Service
public class AmqpAdapter {

    @Autowired
    private QpidConnectionListener qpidConnectionListener;

    @Autowired
    private AmqpConfig amqpConfig;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private AmqpConverter amqpConverter;

    @Autowired
    private AmqpAdapterRepository amqpAdapterRepository;
//
//    /**
//     * Jms模板定时拉取
//     * 待测试
//     */
//    @Scheduled(fixedDelay = 10000)
//    public void JmsReceive(){
//        AmqpRecordDTO amqpRecordDTO = jmsMessagingTemplate.receiveAndConvert(amqpConfig.getTopicName(), AmqpRecordDTO.class);
//        //amqpAdapterRepository.save(amqpConverter.toSource(amqpRecordDTO));
//    }

    /**
     * 原生方法推拉模式
     * @throws Exception
     */
    public void pureReceive() throws Exception {
        Hashtable<String, String> hashtable = buildContext();

        Context context = new InitialContext(hashtable);
        JmsConnectionFactory cf = (JmsConnectionFactory) context.lookup("HwConnectionURL");
        //同一个链接可创建多个queue,与前面queue.HwQueueName作好配对就行
        Destination queue = (Destination) context.lookup("HwQueueName");

        //信任服务端
        TransportOptions to = new TransportOptions();
        to.setTrustAll(true);
        cf.setSslContext(TransportSupport.createJdkSslContext(to));

        //连接凭证接入键值。
        //UserName组装方法，请参见文档：AMQP客户端接入说明。
        //时间戳
        long timeStamp = System.currentTimeMillis();
        String userName = "accessKey=" + amqpConfig.getAccessKey() + "|timestamp=" + timeStamp;

        // 创建连接
        Connection connection = cf.createConnection(userName, amqpConfig.getPassword());
        ((JmsConnection) connection).addConnectionListener(qpidConnectionListener);
        // 创建 Session
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        // 创建 Receiver Link
        MessageConsumer consumer = session.createConsumer(queue);
        //处理消息有两种方式
        // 1，主动拉数据（推荐），参照receiveMessage(consumer)
        // 2, 添加监听，参照consumer.setMessageListener(messageListener), 服务端主动推数据给客户端，但得考虑接受的数据速率是客户能力能够承受住的
        receiveMessage(consumer);
        // consumer.setMessageListener(messageListener);
    }

    private Hashtable<String, String> buildContext(){
        Hashtable<String, String> hashtable = new Hashtable<>();
        //按照qpid-jms的规范，组装连接URL。
        hashtable.put("connectionfactory.HwConnectionURL", amqpConfig.getConnectionUrl());
        //队列名，可以使用默认队列DefaultQueue
        hashtable.put("queue.HwQueueName", amqpConfig.getTopicName());
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        return hashtable;
    }

    private void receiveMessage(MessageConsumer consumer) throws JMSException {
        //需要根据需求修改监听时间
        while (true){
            try{
                // 建议异步处理收到的消息，确保receiveMessage函数里没有耗时逻辑。
                Message message = consumer.receive();
                processMessage(message);
            } catch (Exception e) {
                System.out.println("receiveMessage hand an exception: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private void processMessage(Message message) {
        try {
            String body = message.getBody(String.class);
            String content = new String(body);

//            System.out.println("receive an message, the content is " + content);
            log.info("receive an message, the content is [{}]" ,content);
        } catch (Exception e){
//            System.out.println("processMessage occurs error: " + e.getMessage());
            log.error("processMessage occurs error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
