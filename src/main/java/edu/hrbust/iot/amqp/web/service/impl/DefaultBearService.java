package edu.hrbust.iot.amqp.web.service.impl;

import edu.hrbust.iot.amqp.web.dao.BearRecordRepository;
import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;
import edu.hrbust.iot.amqp.web.utils.converter.BearPDConverter;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordDTO;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecord;
import edu.hrbust.iot.amqp.web.service.BearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Service
public class DefaultBearService implements BearService {

    @Autowired
    private BearRecordRepository repository;

    @Autowired
    private BearPDConverter bearConverter;

    @Override
    public List<BearRecordDTO> queryAll() {
        List<BearRecord> amqpRecords = repository.findAll();
        log.info("{},amqpRecords:{}", LocalDateTime.now(), amqpRecords);
        if (amqpRecords.isEmpty()){
            return Collections.emptyList();
        }
        return bearConverter.toTargetList(amqpRecords);
    }

    @Override
    public PageDTO<BearRecordDTO> queryPage(AmqpQuery amqpQuery) {
        PageDTO<BearRecordDTO> res = PageDTO.emptyPage();

        Specification<BearRecord> queryCondition = buildSpecification(amqpQuery);
        PageRequest pageRequest = PageRequest.of(amqpQuery.getIndex()-1, amqpQuery.getPageSize());
        log.info("{}, index:{}, pageSize:{}", LocalDateTime.now(), amqpQuery.getIndex(), amqpQuery.getPageSize());

        Page<BearRecord> amqpRecordPage = repository.findAll(queryCondition, pageRequest);
        res.setTotalPage(amqpRecordPage.getTotalPages());
        res.setTotal(amqpRecordPage.getTotalElements());
        res.setData(bearConverter.toTargetList(amqpRecordPage.getContent()));

        log.debug("{},page:{}", LocalDateTime.now(), res);
        return res;
    }


    private Specification<BearRecord> buildSpecification(AmqpQuery amqpQuery) {
        return (root, criteriaQuery, criteriaBuilder) ->{
            List<Predicate> predicateList = new ArrayList<>();
            return criteriaBuilder.and(predicateList.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }

    @Override
    public void save(BearRecordDTO record){
        if (record.getUserName() == null) record.setUserName("吴肖琪");
        record.setId(null);
        record.setCreatedTime(new Date());
        repository.save(bearConverter.toSource(record));
    }


}
