package edu.hrbust.iot.amqp.adapter.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.springframework.stereotype.Component;

import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.net.URI;

@Slf4j
@Component
public class QpidConnectionListener implements JmsConnectionListener {

    /** 连接成功建立 */
    @Override
    public void onConnectionEstablished(URI remoteURI){
        log.info("连接建立: {}", remoteURI );
    }


    /** 尝试过最大重试次数之后，最终连接失败 */
    @Override
    public void onConnectionFailure(Throwable error){
        log.error("onConnectionFailure:[{}]", error.getMessage());
    }

    /** 连接中断 */
    @Override
    public void onConnectionInterrupted(URI remoteURI){
        log.error("onConnectionInterrupted, remoteUri:[{}]", remoteURI);
    }

    /**  连接中断后又自动重连上 */
    @Override
    public void onConnectionRestored(URI remoteURI){
        log.info("onConnectionRestored, remoteUri:[{}]", remoteURI);
    }

    @Override
    public void onInboundMessage(JmsInboundMessageDispatch envelope){
        log.debug("envelope:[{}]", envelope );
    }

    @Override
    public void onSessionClosed(Session session, Throwable cause){
        log.error("onSessionClosed, session:[{}], cause:[{}]", session, cause);
    }

    @Override
    public void onConsumerClosed(MessageConsumer consumer, Throwable cause){
        log.error("MessageConsumer, consumer:[{}], cause:[{}]", consumer, cause );
    }

    @Override
    public void onProducerClosed(MessageProducer producer, Throwable cause){
        log.error("MessageProducer, producer:[{}], cause:[{}]", producer, cause);
    }

}
