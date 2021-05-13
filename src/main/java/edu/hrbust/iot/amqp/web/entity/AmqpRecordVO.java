package edu.hrbust.iot.amqp.web.entity;

import edu.hrbust.iot.amqp.web.utils.common.BaseVO;
import lombok.Data;

@Data
public class AmqpRecordVO extends BaseVO {
    private String record;
}
