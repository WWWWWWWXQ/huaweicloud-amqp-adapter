package edu.hrbust.iot.amqp.adapter.entity;

import lombok.Data;

import java.util.List;

@Data
public class NotifyData {
    private Header header;
    private Body body;
}


