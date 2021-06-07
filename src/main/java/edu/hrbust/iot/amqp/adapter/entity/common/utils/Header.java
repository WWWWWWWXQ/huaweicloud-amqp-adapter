package edu.hrbust.iot.amqp.adapter.entity.common.utils;

import lombok.Data;

@Data
public class Header {
    private String appId;
    private String deviceId;
    private String nodeId;
    private String productId;
    private String gatewayId;
}
