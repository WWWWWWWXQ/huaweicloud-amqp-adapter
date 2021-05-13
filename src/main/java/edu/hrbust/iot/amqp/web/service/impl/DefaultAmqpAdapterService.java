package edu.hrbust.iot.amqp.web.service.impl;

import edu.hrbust.iot.amqp.adapter.QpidJmsTemplate;
import edu.hrbust.iot.amqp.web.dao.AmqpAdapterRepository;
import edu.hrbust.iot.amqp.web.utils.converter.AmqpConverter;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.entity.AmqpRecord;
import edu.hrbust.iot.amqp.web.service.AmqpAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DefaultAmqpAdapterService implements AmqpAdapterService {


    @Autowired
    private AmqpAdapterRepository amqpAdapterRepository;

    @Autowired
    private QpidJmsTemplate qpidJmsTemplate;

    @Override
    public List<AmqpRecordDTO> queryAll() {
        List<AmqpRecord> amqpRecords = amqpAdapterRepository.findAll();
        return amqpConverter.toTargetList(amqpRecords);
    }
    private void save(AmqpRecordDTO record){
        amqpAdapterRepository.save(amqpConverter.toSource(record));
    }

    @Autowired
    private AmqpConverter amqpConverter;

    private void receive(){
        try {
            qpidJmsTemplate.testReceive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
