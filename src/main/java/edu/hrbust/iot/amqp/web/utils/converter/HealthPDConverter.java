package edu.hrbust.iot.amqp.web.utils.converter;

import edu.hrbust.iot.amqp.web.entity.heart.HealthData;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.utils.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class HealthPDConverter implements BaseConverter<HealthData, HealthDataDTO> {
}
