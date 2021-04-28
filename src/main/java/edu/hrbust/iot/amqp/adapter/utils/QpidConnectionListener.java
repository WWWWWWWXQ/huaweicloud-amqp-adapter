package edu.hrbust.iot.amqp.adapter.utils;

import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.springframework.stereotype.Component;

import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.net.URI;

@Component
public class QpidConnectionListener implements JmsConnectionListener {

    /**
     * 连接成功建立。
     */
    @Override
    public void onConnectionEstablished(URI remoteURI){
        System.out.println("onConnectionEstablished, remoteUri:" + remoteURI);
    }

    /**
     * 尝试过最大重试次数之后，最终连接失败。
     */
    @Override
    public void onConnectionFailure(Throwable error){
        System.out.println("onConnectionFailure, " + error.getMessage());
    }

    /**
     * 连接中断。
     */
    @Override
    public void onConnectionInterrupted(URI remoteURI){
        System.out.println("onConnectionInterrupted, remoteUri:" + remoteURI);
    }

    /**
     * 连接中断后又自动重连上。
     */
    @Override
    public void onConnectionRestored(URI remoteURI){
        System.out.println("onConnectionRestored, remoteUri:" + remoteURI);
    }

    @Override
    public void onInboundMessage(JmsInboundMessageDispatch envelope){
        System.out.println("onInboundMessage, " + envelope);
    }

    @Override
    public void onSessionClosed(Session session, Throwable cause){
        System.out.println("onSessionClosed, session=" + session + ", cause =" + cause);
    }

    @Override
    public void onConsumerClosed(MessageConsumer consumer, Throwable cause){
        System.out.println("MessageConsumer, consumer=" + consumer + ", cause =" + cause);
    }

    @Override
    public void onProducerClosed(MessageProducer producer, Throwable cause){
        System.out.println("MessageProducer, producer=" + producer + ", cause =" + cause);
    }
}
