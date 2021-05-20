package edu.hrbust.iot.amqp.web.utils.converter;

import edu.hrbust.iot.amqp.web.utils.common.base.BaseConverter;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.entity.AmqpRecord;
import org.springframework.stereotype.Component;

@Component
public class AmqpConverter implements BaseConverter<AmqpRecord, AmqpRecordDTO> {

}
