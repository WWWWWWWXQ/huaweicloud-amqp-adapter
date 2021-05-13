package edu.hrbust.iot.amqp.web.entity;

import edu.hrbust.iot.amqp.web.utils.common.BasePO;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class AmqpRecord extends BasePO {

    private String record;

}
