package edu.hrbust.iot.amqp.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AmqpQuery {

    private int index;
    private int pageSize;
}
