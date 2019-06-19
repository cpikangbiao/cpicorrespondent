package com.cpi.correspondent.service;

import com.cpi.correspondent.domain.CorrespondentBillStatusLog;
import com.cpi.correspondent.domain.CorrespondentBillStatusLog_;
import com.cpi.correspondent.domain.CorrespondentBill_;
import com.cpi.correspondent.repository.CorrespondentBillStatusLogRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogCriteria;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusLogMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link CorrespondentBillStatusLog} entities in the database.
 * The main input is a {@link CorrespondentBillStatusLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentBillStatusLogDTO} or a {@link Page} of {@link CorrespondentBillStatusLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentBillStatusLogExtService extends QueryService<CorrespondentBillStatusLog> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusLogExtService.class);

    private final CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository;

    private final CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper;

    public CorrespondentBillStatusLogExtService(CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository, CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper) {
        this.correspondentBillStatusLogRepository = correspondentBillStatusLogRepository;
        this.correspondentBillStatusLogMapper = correspondentBillStatusLogMapper;
    }



    @Transactional(readOnly = true)
    public List<CorrespondentBillStatusLogDTO> findByCorrespondentBillId(Long correspondentBillId) {
        log.debug("find by correspondentBillId : {}", correspondentBillId);
        return correspondentBillStatusLogMapper.toDto(correspondentBillStatusLogRepository.findByCorrespondentBillId(correspondentBillId));
    }

}
