package edu.hrbust.iot.amqp.adapter.entity.bear;

import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import lombok.Data;

@Data
public class BearProperties extends Properties {
    private int luminance;
    private int newinttest;
    private String newstr;
}
