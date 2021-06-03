package edu.hrbust.iot.amqp.adapter.entity.heart;

import edu.hrbust.iot.amqp.adapter.entity.common.NotifyData;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AmqpHealth {
    private String resource;
    private String event;
    private Date eventTime;
    private NotifyData<HealthProperties> notifyData;
}
