package edu.hrbust.iot.amqp.web.service;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordDTO;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;

import java.util.List;

public interface BearService {

    List<BearRecordDTO> queryAll();

    PageDTO<BearRecordDTO> queryPage(AmqpQuery amqpQuery);

    void save(BearRecordDTO bearRecordDTO);
}
