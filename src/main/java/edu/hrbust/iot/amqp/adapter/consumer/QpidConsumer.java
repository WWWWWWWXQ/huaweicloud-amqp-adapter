package edu.hrbust.iot.amqp.adapter.consumer;

import edu.hrbust.iot.amqp.adapter.consumer.QpidJmsTemplate;
import edu.hrbust.iot.amqp.adapter.entity.common.BaseMessageTemplate;
import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消费者模版类
 * 编写自定义消费者接收消息并入库的流程为:
 * <pre>
 * 1. 编写实体类
 *   - 继承 {@link Properties} 的自定义属性类;
 *   - 继承 {@link BaseMessageTemplate} 的AMQP消息类
 * 2. 编写继承本类的消费者，编写并提供对应的 Service 和 Converter
 *   - Service: 入库的逻辑处理类，可以是Web后端调用DAO层的逻辑类
 *   - Converter: 属性转换工具，将云平台配置的字段与数据库表的字段进行转换
 * 3. 调用 qpidJmsTemplate 提供的方法接收消息，并提供入库操作
 * </pre>
 *
 * @see edu.hrbust.iot.amqp.adapter.consumer.HealthConsumer
 * @author WWWWWWWXQ
 */
public abstract class QpidConsumer {

    @Autowired
    protected QpidJmsTemplate qpidJmsTemplate;

}
