package edu.hrbust.iot.amqp.adapter.entity.heart;

import edu.hrbust.iot.amqp.adapter.entity.common.BaseMessageTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class HealthAmqpMsg extends BaseMessageTemplate<HealthProperties> {

}