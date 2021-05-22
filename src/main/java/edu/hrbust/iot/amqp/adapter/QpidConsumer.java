package edu.hrbust.iot.amqp.adapter;

import edu.hrbust.iot.amqp.adapter.entity.bear.AmqpMsgBearConverter;
import edu.hrbust.iot.amqp.adapter.entity.bear.AmqpBear;
import edu.hrbust.iot.amqp.adapter.entity.heart.AmqpMsgHeartConverter;
import edu.hrbust.iot.amqp.adapter.entity.heart.AmqpHealth;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordDTO;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.service.BearService;
import edu.hrbust.iot.amqp.web.service.HealthService;
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
    private BearService bearService;

    @Autowired
    private HealthService heartHealthService;

    @Autowired
    private QpidJmsTemplate qpidJmsTemplate;

    @Autowired
    private AmqpMsgBearConverter bearConverter;

    @Autowired
    private AmqpMsgHeartConverter heartConverter;

//    @Scheduled(fixedDelay = 60000)
    public void receiveBear(){
        log.info("{}, [定时任务开始]", LocalDateTime.now());
//        List<Bear> bears = qpidJmsTemplate.receiveAndConvertToJson(Bear.class);
//        log.info("共接收到{}条消息", bears.size());
//
//        if (!bears.isEmpty()) {
////            bears.forEach(b->log.info("bear:{}", b));
//            log.info("{}, [准备入库], 入库信息为: {}",LocalDateTime.now(), bears);
//            bears.forEach(this::save);
//            log.info("{}, [已入库，定时任务结束]", LocalDateTime.now());
//        }
    }

    /**
     * 入库
     * @param bear
     */
    public void save(AmqpBear bear){
        BearRecordDTO bearRecordDTO = bearConverter.convertToDTO(bear);
        bearService.save(bearRecordDTO);
    }


    @Scheduled(fixedDelay = 300000)
    public void receiveHealthData() {
        log.info("[定时任务开始], {}", LocalDateTime.now());
        List<AmqpHealth> healthData = qpidJmsTemplate.receiveAndConvertToJson(AmqpHealth.class);
        log.info("共接收到 [{}] 条消息", healthData.size());

        if (!healthData.isEmpty()) {
//            bears.forEach(b->log.info("bear:{}", b));
            log.info("[准备入库], {}, 入库信息为: {}",LocalDateTime.now(), healthData);
            healthData.forEach(this::save);
            log.info("[已入库，定时任务结束], {} ", LocalDateTime.now());
        }
    }

    public void save(AmqpHealth healthData){
        HealthDataDTO healthDataDTO = heartConverter.convertToDTO(healthData);
        heartHealthService.save(healthDataDTO);
    }
}
