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

import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillCriteria;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeAndBillMapper;

/**
 * Service for executing complex queries for {@link CorrespondentFeeAndBill} entities in the database.
 * The main input is a {@link CorrespondentFeeAndBillCriteria} which gets converted to {@link Specification},
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
     * Return a {@link List} of {@link CorrespondentFeeAndBillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentFeeAndBillDTO> findByCriteria(CorrespondentFeeAndBillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CorrespondentFeeAndBill> specification = createSpecification(criteria);
        return correspondentFeeAndBillMapper.toDto(correspondentFeeAndBillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentFeeAndBillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeAndBillDTO> findByCriteria(CorrespondentFeeAndBillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CorrespondentFeeAndBill> specification = createSpecification(criteria);
        return correspondentFeeAndBillRepository.findAll(specification, page)
            .map(correspondentFeeAndBillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorrespondentFeeAndBillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CorrespondentFeeAndBill> specification = createSpecification(criteria);
        return correspondentFeeAndBillRepository.count(specification);
    }

    /**
     * Function to convert CorrespondentFeeAndBillCriteria to a {@link Specification}.
     */
    private Specification<CorrespondentFeeAndBill> createSpecification(CorrespondentFeeAndBillCriteria criteria) {
        Specification<CorrespondentFeeAndBill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentFeeAndBill_.id));
            }
            if (criteria.getCorrespondentDebitBillId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrespondentDebitBillId(),
                    root -> root.join(CorrespondentFeeAndBill_.correspondentDebitBill, JoinType.LEFT).get(CorrespondentBill_.id)));
            }
            if (criteria.getCorrespondentFeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrespondentFeeId(),
                    root -> root.join(CorrespondentFeeAndBill_.correspondentFee, JoinType.LEFT).get(CorrespondentFee_.id)));
            }
            if (criteria.getCorrespondentCreditBillId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrespondentCreditBillId(),
                    root -> root.join(CorrespondentFeeAndBill_.correspondentCreditBill, JoinType.LEFT).get(CorrespondentBill_.id)));
            }
        }
        return specification;
    }
}
