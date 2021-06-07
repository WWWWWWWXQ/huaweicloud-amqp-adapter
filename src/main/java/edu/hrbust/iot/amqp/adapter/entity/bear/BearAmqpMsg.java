package edu.hrbust.iot.amqp.adapter.entity.bear;

import edu.hrbust.iot.amqp.adapter.entity.common.BaseMessageTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BearAmqpMsg extends BaseMessageTemplate<BearProperties> {

}