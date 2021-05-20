package edu.hrbust.iot.amqp.web.service;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;

import java.util.List;

public interface AmqpAdapterService {

    List<AmqpRecordDTO> queryAll();

    PageDTO<AmqpRecordDTO> queryPage(AmqpQuery amqpQuery);

    void save(AmqpRecordDTO amqpRecordDTO);
}
