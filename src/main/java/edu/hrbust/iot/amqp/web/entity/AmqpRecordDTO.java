package edu.hrbust.iot.amqp.web.entity;

import edu.hrbust.iot.amqp.web.utils.common.BaseDTO;
import lombok.Data;

@Data
public class AmqpRecordDTO extends BaseDTO {
    private String record;
}
