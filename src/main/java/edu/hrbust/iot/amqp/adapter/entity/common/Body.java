package edu.hrbust.iot.amqp.adapter.entity.common;

import lombok.Data;

import java.util.List;

@Data
public class Body<T extends Properties> {
    private List<Services<T>> services;
}