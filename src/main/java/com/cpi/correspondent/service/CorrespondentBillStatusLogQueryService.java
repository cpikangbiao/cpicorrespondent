package com.cpi.correspondent.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.correspondent.domain.CorrespondentBillStatusLog;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentBillStatusLogRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogCriteria;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusLogMapper;

/**
 * Service for executing complex queries for {@link CorrespondentBillStatusLog} entities in the database.
 * The main input is a {@link CorrespondentBillStatusLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentBillStatusLogDTO} or a {@link Page} of {@link CorrespondentBillStatusLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentBillStatusLogQueryService extends QueryService<CorrespondentBillStatusLog> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusLogQueryService.class);

    private final CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository;

    private final CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper;

    public CorrespondentBillStatusLogQueryService(CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository, CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper) {
        this.correspondentBillStatusLogRepository = correspondentBillStatusLogRepository;
        this.correspondentBillStatusLogMapper = correspondentBillStatusLogMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentBillStatusLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentBillStatusLogDTO> findByCriteria(CorrespondentBillStatusLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CorrespondentBillStatusLog> specification = createSpecification(criteria);
        return correspondentBillStatusLogMapper.toDto(correspondentBillStatusLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentBillStatusLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentBillStatusLogDTO> findByCriteria(CorrespondentBillStatusLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CorrespondentBillStatusLog> specification = createSpecification(criteria);
        return correspondentBillStatusLogRepository.findAll(specification, page)
            .map(correspondentBillStatusLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorrespondentBillStatusLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CorrespondentBillStatusLog> specification = createSpecification(criteria);
        return correspondentBillStatusLogRepository.count(specification);
    }

    /**
     * Function to convert CorrespondentBillStatusLogCriteria to a {@link Specification}.
     */
    private Specification<CorrespondentBillStatusLog> createSpecification(CorrespondentBillStatusLogCriteria criteria) {
        Specification<CorrespondentBillStatusLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentBillStatusLog_.id));
            }
            if (criteria.getBillStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillStatusName(), CorrespondentBillStatusLog_.billStatusName));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), CorrespondentBillStatusLog_.updateTime));
            }
            if (criteria.getUpdateUser() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateUser(), CorrespondentBillStatusLog_.updateUser));
            }
            if (criteria.getCorrespondentBillId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrespondentBillId(),
                    root -> root.join(CorrespondentBillStatusLog_.correspondentBill, JoinType.LEFT).get(CorrespondentBill_.id)));
            }
        }
        return specification;
    }
}
