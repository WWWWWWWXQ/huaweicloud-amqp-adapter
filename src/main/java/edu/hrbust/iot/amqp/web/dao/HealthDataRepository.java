package edu.hrbust.iot.amqp.web.dao;

import edu.hrbust.iot.amqp.web.entity.heart.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface HealthDataRepository extends
        JpaRepository<HealthData, Long>, JpaSpecificationExecutor<HealthData> {

}
