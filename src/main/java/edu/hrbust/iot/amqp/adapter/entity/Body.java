package edu.hrbust.iot.amqp.adapter.entity;

import lombok.Data;

import java.util.List;

@Data
public class Body {
    private List<Services> services;
}