package edu.hrbust.iot.amqp.adapter.entity.common;

import edu.hrbust.iot.amqp.adapter.entity.common.Body;
import edu.hrbust.iot.amqp.adapter.entity.common.Header;
import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import lombok.Data;

@Data
public class NotifyData<T extends Properties> {
    private Header header;
    private Body<T> body;
}


