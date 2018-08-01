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

import com.cpi.correspondent.domain.CorrespondentBillStatus;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentBillStatusRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusCriteria;

import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusMapper;

/**
 * Service for executing complex queries for CorrespondentBillStatus entities in the database.
 * The main input is a {@link CorrespondentBillStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentBillStatusDTO} or a {@link Page} of {@link CorrespondentBillStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentBillStatusQueryService extends QueryService<CorrespondentBillStatus> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusQueryService.class);


    private final CorrespondentBillStatusRepository correspondentBillStatusRepository;

    private final CorrespondentBillStatusMapper correspondentBillStatusMapper;

    public CorrespondentBillStatusQueryService(CorrespondentBillStatusRepository correspondentBillStatusRepository, CorrespondentBillStatusMapper correspondentBillStatusMapper) {
        this.correspondentBillStatusRepository = correspondentBillStatusRepository;
        this.correspondentBillStatusMapper = correspondentBillStatusMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentBillStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentBillStatusDTO> findByCriteria(CorrespondentBillStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentBillStatus> specification = createSpecification(criteria);
        return correspondentBillStatusMapper.toDto(correspondentBillStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentBillStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentBillStatusDTO> findByCriteria(CorrespondentBillStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentBillStatus> specification = createSpecification(criteria);
        final Page<CorrespondentBillStatus> result = correspondentBillStatusRepository.findAll(specification, page);
        return result.map(correspondentBillStatusMapper::toDto);
    }

    /**
     * Function to convert CorrespondentBillStatusCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentBillStatus> createSpecification(CorrespondentBillStatusCriteria criteria) {
        Specifications<CorrespondentBillStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentBillStatus_.id));
            }
            if (criteria.getCorrespondentBillStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentBillStatusName(), CorrespondentBillStatus_.correspondentBillStatusName));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), CorrespondentBillStatus_.sortNum));
            }
        }
        return specification;
    }

}
