package com.cpi.correspondent.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
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
 * Service for executing complex queries for CorrespondentBillStatusLog entities in the database.
 * The main input is a {@link CorrespondentBillStatusLogCriteria} which get's converted to {@link Specifications},
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
     * Return a {@link List} of {@link CorrespondentBillStatusLogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentBillStatusLogDTO> findByCriteria(CorrespondentBillStatusLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentBillStatusLog> specification = createSpecification(criteria);
        return correspondentBillStatusLogMapper.toDto(correspondentBillStatusLogRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public List<CorrespondentBillStatusLogDTO> findByCorrespondentBillId(Long correspondentBillId) {
        log.debug("find by correspondentBillId : {}", correspondentBillId);
        return correspondentBillStatusLogMapper.toDto(correspondentBillStatusLogRepository.findByCorrespondentBillId(correspondentBillId));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentBillStatusLogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentBillStatusLogDTO> findByCriteria(CorrespondentBillStatusLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentBillStatusLog> specification = createSpecification(criteria);
        final Page<CorrespondentBillStatusLog> result = correspondentBillStatusLogRepository.findAll(specification, page);
        return result.map(correspondentBillStatusLogMapper::toDto);
    }

    /**
     * Function to convert CorrespondentBillStatusLogCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentBillStatusLog> createSpecification(CorrespondentBillStatusLogCriteria criteria) {
        Specifications<CorrespondentBillStatusLog> specification = Specifications.where(null);
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
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentBillId(), CorrespondentBillStatusLog_.correspondentBill, CorrespondentBill_.id));
            }
        }
        return specification;
    }

}
