package edu.hrbust.iot.amqp.adapter.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Topic模式下
 * 监听模式
 */
@Component
@Slf4j
public class QpidConsumeListener {

    //监听模式下为防止客户端并发压力过大，采用多线程模式
    //异步线程池，参数可以根据业务特点作调整，也可以用其他异步方式来处理。
    private final static ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5000)
    );

//    @JmsListener(destination = "${huawei.qpid.topicName}" )
//    public void receive(Message message){
//        executorService.submit(() -> {
//            try {
//                log.info("收到消息 [{}], at [{}]", message.getBody(String.class), LocalDateTime.now());
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    @JmsListener(destination = "${huawei.qpid.topicName}", concurrency = "5")
    public void receive(Message message){
        log.info("receive message:{}", message);
    }
}
