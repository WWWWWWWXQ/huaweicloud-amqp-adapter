package edu.hrbust.iot.amqp.adapter.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class Bear {
    private String resource;
    private String event;
    private Date eventTime;
    private NotifyData notifyData;

}
