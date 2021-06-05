package edu.hrbust.iot.amqp.adapter.entity.common.utils;

import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import lombok.Data;

import java.util.List;

/**
 *
 * @param <T>
 */
@Data
public class Body<T extends Properties> {
    private List<Services<T>> services;
}