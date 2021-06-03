package edu.hrbust.iot.amqp.web.utils.converter;

import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataVO;
import edu.hrbust.iot.amqp.web.utils.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class HealthDVConverter implements BaseConverter<HealthDataDTO, HealthDataVO> {
}
