package edu.hrbust.iot.amqp.adapter.entity.common;

import edu.hrbust.iot.amqp.adapter.entity.common.utils.NotifyData;
import lombok.Data;

import java.util.Date;

/**
 * 华为云 IoTDA 推送消息模板
 * <pre> {@code
 * {
 *   "resource": "example",
 *   "event": "report",
 *   "event_time": "20210521T122229Z",
 *   "notify_data": {
 *     "header": {
 *       "app_id": "e6c196d40df84846bacbd9573dabb685",
 *       "device_id": "5fd6df9937f2a30303b55693_863434047705329",
 *       "node_id": "863434047705329",
 *       "product_id": "5fd6df9937f2a30303b55693",
 *       "gateway_id": "5fd6df9937f2a30303b55693_863434047705329"
 *     },
 *     "body": {
 *       "services": [{
 *         "service_id": "example",
 *           "properties": {
 *               // 自定义的消息属性
 *               // 对应华为云 IoTDA 平台上配置的字段信息
 *           },
 *           "event_time": "20210521T122229Z"
 *       }]
 *     }
 *   }
 * }} </pre>
 *
 * @param <P> 自定义的消息属性类，需继承规范类 Properties
 */
@Data
public class BaseMessageTemplate<P extends Properties> {
    private String resource;
    private String event;
    private Date eventTime;
    private NotifyData<P> notifyData;
}
