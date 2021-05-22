package edu.hrbust.iot.amqp.web.service;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;


public interface HealthService {

    PageDTO<HealthDataDTO> queryPage(AmqpQuery amqpQuery);

    void save(HealthDataDTO healthDataDTO);

}
