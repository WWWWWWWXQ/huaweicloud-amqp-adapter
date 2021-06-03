package edu.hrbust.iot.amqp.web.dao;

import edu.hrbust.iot.amqp.web.entity.bear.BearRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface BearRecordRepository extends
        JpaRepository<BearRecord, Long>, JpaSpecificationExecutor<BearRecord> {

}
