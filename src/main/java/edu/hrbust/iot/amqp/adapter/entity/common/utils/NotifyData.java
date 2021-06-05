package edu.hrbust.iot.amqp.adapter.entity.common.utils;

import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import lombok.Data;

@Data
public class NotifyData<T extends Properties> {
    private Header header;
    private Body<T> body;
}


