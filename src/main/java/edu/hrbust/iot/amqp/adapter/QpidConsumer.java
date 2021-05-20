package edu.hrbust.iot.amqp.adapter;

import edu.hrbust.iot.amqp.adapter.QpidJmsTemplate;
import edu.hrbust.iot.amqp.adapter.base.AmqpMsgConverter;
import edu.hrbust.iot.amqp.adapter.entity.Bear;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.service.AmqpAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;



/**
 * Topic模式下
 * 监听模式
 */
@Component
@Slf4j
public class QpidConsumer {

    @Autowired
    private AmqpAdapterService amqpAdapterService;

    @Autowired
    private QpidJmsTemplate qpidJmsTemplate;

    @Autowired
    private AmqpMsgConverter amqpMsgConverter;

    @Scheduled(fixedDelay = 60000)
    public void receive(){
        log.info("{}, [定时任务开始]", LocalDateTime.now());
        List<Bear> bears = qpidJmsTemplate.receiveAndConvertToJson(Bear.class);
        log.info("共接收到{}条消息", bears.size());

        if (!bears.isEmpty()) {
//            bears.forEach(b->log.info("bear:{}", b));
            log.info("{}, [准备入库], 入库信息为: {}",LocalDateTime.now(), bears);
            bears.forEach(this::save);
            log.info("{}, [已入库，定时任务结束]", LocalDateTime.now());
        }
    }

    /**
     * 入库
     * @param bear
     */
    public void save(Bear bear){
        AmqpRecordDTO amqpRecordDTO = amqpMsgConverter.convertToDTO(bear);
        amqpAdapterService.save(amqpRecordDTO);
    }

}
