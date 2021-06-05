package edu.hrbust.iot.amqp.adapter.consumer;

import edu.hrbust.iot.amqp.adapter.base.QpidConsumer;
import edu.hrbust.iot.amqp.adapter.entity.heart.HealthAmqpMsgConverter;
import edu.hrbust.iot.amqp.adapter.entity.heart.HealthAmqpMsg;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.service.HealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 心脏健康信息消费者
 */
@Slf4j
@Component
public class HealthConsumer extends QpidConsumer {

    @Autowired
    private HealthService heartHealthService;

    @Autowired
    private HealthAmqpMsgConverter heartConverter;

    private void save(HealthAmqpMsg healthData){
        HealthDataDTO healthDataDTO = heartConverter.convertToDTO(healthData);
        heartHealthService.save(healthDataDTO);
    }

    //    @Scheduled(fixedDelay = 300000)
    public void receiveHealthData() {
        log.info("[定时任务开始], {}", LocalDateTime.now());
        List<HealthAmqpMsg> healthData = qpidJmsTemplate.receiveAndConvertToJson(HealthAmqpMsg.class);
        log.info("共接收到 [{}] 条消息", healthData.size());

        if (!healthData.isEmpty()) {
            log.info("[准备入库], {}, 入库信息为: {}",LocalDateTime.now(), healthData);
            healthData.forEach(this::save);
            log.info("[已入库，定时任务结束], {} ", LocalDateTime.now());
        }
    }

}

