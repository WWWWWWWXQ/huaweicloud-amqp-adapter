package edu.hrbust.iot.amqp.adapter.entity.bear;

import edu.hrbust.iot.amqp.adapter.entity.common.NotifyData;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AmqpBear {
    private String resource;
    private String event;
    private Date eventTime;
    private NotifyData<BearProperties> notifyData;

}
