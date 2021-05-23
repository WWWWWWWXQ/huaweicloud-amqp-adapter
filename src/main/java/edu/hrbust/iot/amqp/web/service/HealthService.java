package edu.hrbust.iot.amqp.web.service;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;

import java.util.List;

/**
 * Health逻辑类
 */
public interface HealthService {

    PageDTO<HealthDataDTO> queryPage(AmqpQuery amqpQuery);

    void save(HealthDataDTO healthDataDTO);

    List<HealthDataDTO> queryAll();
}
