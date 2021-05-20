package edu.hrbust.iot.amqp.web.dao;

import edu.hrbust.iot.amqp.web.entity.AmqpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmqpAdapterRepository extends
        JpaRepository<AmqpRecord, Long>, JpaSpecificationExecutor<AmqpRecord> {

}
