package edu.hrbust.iot.amqp.web.service;

import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;

import java.util.List;

public interface AmqpAdapterService {

    List<AmqpRecordDTO> queryAll();

}
