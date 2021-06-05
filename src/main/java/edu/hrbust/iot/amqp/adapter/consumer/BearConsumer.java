package edu.hrbust.iot.amqp.adapter.consumer;

import edu.hrbust.iot.amqp.adapter.base.QpidConsumer;
import edu.hrbust.iot.amqp.adapter.entity.bear.BearAmqpMsg;
import edu.hrbust.iot.amqp.adapter.entity.bear.BearAmqpMsgConverter;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordDTO;
import edu.hrbust.iot.amqp.web.service.BearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 小熊派消费者
 */
@Slf4j
@Component
public class BearConsumer extends QpidConsumer {

    @Autowired
    private BearService bearService;

    @Autowired
    private BearAmqpMsgConverter bearConverter;

    private void save(BearAmqpMsg bearAmqpMsg){
        BearRecordDTO bearRecordDTO = bearConverter.convertToDTO(bearAmqpMsg);
        bearService.save(bearRecordDTO);
    }

    public void reveice(){
        List<BearAmqpMsg> bearAmqpMsgs = qpidJmsTemplate.receiveAndConvertToJson(BearAmqpMsg.class);
        log.info("共收到[{}]条消息", bearAmqpMsgs.size());

        if (!bearAmqpMsgs.isEmpty())
             bearAmqpMsgs.forEach(this::save);
    }

}
