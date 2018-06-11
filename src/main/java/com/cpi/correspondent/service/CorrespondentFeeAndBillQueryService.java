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

import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillCriteria;

import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeAndBillMapper;

/**
 * Service for executing complex queries for CorrespondentFeeAndBill entities in the database.
 * The main input is a {@link CorrespondentFeeAndBillCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentFeeAndBillDTO} or a {@link Page} of {@link CorrespondentFeeAndBillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentFeeAndBillQueryService extends QueryService<CorrespondentFeeAndBill> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeAndBillQueryService.class);


    private final CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    private final CorrespondentFeeAndBillMapper correspondentFeeAndBillMapper;

    public CorrespondentFeeAndBillQueryService(CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository, CorrespondentFeeAndBillMapper correspondentFeeAndBillMapper) {
        this.correspondentFeeAndBillRepository = correspondentFeeAndBillRepository;
        this.correspondentFeeAndBillMapper = correspondentFeeAndBillMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentFeeAndBillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentFeeAndBillDTO> findByCriteria(CorrespondentFeeAndBillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentFeeAndBill> specification = createSpecification(criteria);
        return correspondentFeeAndBillMapper.toDto(correspondentFeeAndBillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentFeeAndBillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeAndBillDTO> findByCriteria(CorrespondentFeeAndBillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentFeeAndBill> specification = createSpecification(criteria);
        final Page<CorrespondentFeeAndBill> result = correspondentFeeAndBillRepository.findAll(specification, page);
        return result.map(correspondentFeeAndBillMapper::toDto);
    }

    /**
     * Function to convert CorrespondentFeeAndBillCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentFeeAndBill> createSpecification(CorrespondentFeeAndBillCriteria criteria) {
        Specifications<CorrespondentFeeAndBill> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentFeeAndBill_.id));
            }
            if (criteria.getCorrespondentDebitBillId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentDebitBillId(), CorrespondentFeeAndBill_.correspondentDebitBill, CorrespondentBill_.id));
            }
            if (criteria.getCorrespondentFeeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentFeeId(), CorrespondentFeeAndBill_.correspondentFee, CorrespondentFee_.id));
            }
            if (criteria.getCorrespondentCreditBillId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentCreditBillId(), CorrespondentFeeAndBill_.correspondentCreditBill, CorrespondentBill_.id));
            }
        }
        return specification;
    }

}
