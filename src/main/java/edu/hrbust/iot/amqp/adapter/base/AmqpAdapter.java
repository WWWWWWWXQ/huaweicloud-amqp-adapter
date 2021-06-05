package edu.hrbust.iot.amqp.adapter.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.transports.TransportOptions;
import org.apache.qpid.jms.transports.TransportSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;


/**
 * AMQP Consumer 核心类
 * 遵循 JMS 2.0 规范进行开发，采用 QpidConnectionListener 监听连接状态，
 * 通过 AmqpConfig 注入配置， 利用 ExecutorService 进行线程管理。
 *
 * @since 1.0
 * @author WWWWWWWXQ
 */
@Slf4j
@Component
public class AmqpAdapter {

    private final QpidConnectionListener qpidConnectionListener;
    private final AmqpConfig amqpConfig;

    @Autowired
    public AmqpAdapter(QpidConnectionListener qpidConnectionListener, AmqpConfig amqpConfig) {
        this.qpidConnectionListener = qpidConnectionListener;
        this.amqpConfig = amqpConfig;
    }

    private ExecutorService executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5000));

    public List<Message> receiveMessageList(long timeOut){
        try {
            return receive(timeOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Message> receiveMessageList(){
        try {
            return receive(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 按照 JMS 规范开发 Consumer
     *
     * @param timeOut 超时时间
     * @return List<<Message>Message</Message>> 采用 CopyOnWriteArrayList 储存的jms.Message list
     * @throws Exception Exception
     */
    private List<Message> receive(long timeOut) throws Exception {
        // 将配置属性放入 javax.naming.Context 上下文
        Hashtable<String, String> hashtable = buildContext();
        Context context = new InitialContext(hashtable);
        JmsConnectionFactory cf = (JmsConnectionFactory) context.lookup("HwConnectionURL");

        // 同一个链接可创建多个queue,与前面queue.HwQueueName作好配对就行
        Destination queue = (Destination) context.lookup("HwQueueName");

        // 信任服务端
        TransportOptions to = new TransportOptions();
        to.setTrustAll(true);
        cf.setSslContext(TransportSupport.createJdkSslContext(to));

        // 连接凭证接入键值。
        // UserName组装方法，请参见文档：AMQP客户端接入说明。
        long timeStamp = System.currentTimeMillis();
        String userName = "accessKey=" + amqpConfig.getAccessKey() + "|timestamp=" + timeStamp;
        log.debug(userName);

        // 创建连接
        Connection connection = cf.createConnection(userName, amqpConfig.getPassword());
        ((JmsConnection) connection).addConnectionListener(qpidConnectionListener);

        // 创建AMQP Message 接收对象
        // 采用线程安全集合 CopyOnWriteArrayList 进行高并发情况下的结果接收
        List<Message> messages = new CopyOnWriteArrayList<>();
        try {
            // 创建 Session
            // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
            // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            // 创建 Receiver Link
            MessageConsumer consumer = session.createConsumer(queue);
            try {
                // 开始接受消息
                // 接收时会一直占用连接直到持续 `timeOut` 时间仍未接收到消息
                log.info("[开始接收消息], Time:{},", LocalDateTime.now());
                Message message = consumer.receive(timeOut);
                while (message != null) {
                    poccess(message, messages);
                    message = consumer.receive(timeOut);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                consumer.close();
                session.close();
            }
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 保证程序接收后连接关闭
            connection.close();
            log.info("[消息接收结束], Time:{}, connection是否关闭:{}", LocalDateTime.now(), ((JmsConnection) connection).isClosed());
        }
        return messages;
    }

    /**
     * 异步处理接收到的消息
     * 将message存入list
     * @param message jms.Message
     * @param list java.util.List
     */
    private void poccess(Message message, List<Message> list) {
        executorService.submit(()->{
            list.add(message);
        });
    }

    private Hashtable<String, String> buildContext() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        //按照qpid-jms的规范，组装连接URL。
        hashtable.put("connectionfactory.HwConnectionURL", amqpConfig.getConnectionUrl());
        //队列名，可以使用默认队列DefaultQueue
        hashtable.put("queue.HwQueueName", amqpConfig.getQueueName());
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        return hashtable;
    }
}