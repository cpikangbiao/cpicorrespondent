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

import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentFeeRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeCriteria;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeMapper;

/**
 * Service for executing complex queries for {@link CorrespondentFee} entities in the database.
 * The main input is a {@link CorrespondentFeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentFeeDTO} or a {@link Page} of {@link CorrespondentFeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentFeeQueryService extends QueryService<CorrespondentFee> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeQueryService.class);

    private final CorrespondentFeeRepository correspondentFeeRepository;

    private final CorrespondentFeeMapper correspondentFeeMapper;

    public CorrespondentFeeQueryService(CorrespondentFeeRepository correspondentFeeRepository, CorrespondentFeeMapper correspondentFeeMapper) {
        this.correspondentFeeRepository = correspondentFeeRepository;
        this.correspondentFeeMapper = correspondentFeeMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentFeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentFeeDTO> findByCriteria(CorrespondentFeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CorrespondentFee> specification = createSpecification(criteria);
        return correspondentFeeMapper.toDto(correspondentFeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentFeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeDTO> findByCriteria(CorrespondentFeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CorrespondentFee> specification = createSpecification(criteria);
        return correspondentFeeRepository.findAll(specification, page)
            .map(correspondentFeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorrespondentFeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CorrespondentFee> specification = createSpecification(criteria);
        return correspondentFeeRepository.count(specification);
    }

    /**
     * Function to convert CorrespondentFeeCriteria to a {@link Specification}.
     */
    private Specification<CorrespondentFee> createSpecification(CorrespondentFeeCriteria criteria) {
        Specification<CorrespondentFee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentFee_.id));
            }
            if (criteria.getClientNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientNo(), CorrespondentFee_.clientNo));
            }
            if (criteria.getNumberId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberId(), CorrespondentFee_.numberId));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrency(), CorrespondentFee_.currency));
            }
            if (criteria.getCurrencyRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrencyRate(), CorrespondentFee_.currencyRate));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), CorrespondentFee_.cost));
            }
            if (criteria.getCostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostDate(), CorrespondentFee_.costDate));
            }
            if (criteria.getCostDollar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostDollar(), CorrespondentFee_.costDollar));
            }
            if (criteria.getCorrespondentFeeTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrespondentFeeTypeId(),
                    root -> root.join(CorrespondentFee_.correspondentFeeType, JoinType.LEFT).get(CorrespondentFeeType_.id)));
            }
            if (criteria.getCpiCorrespondentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCpiCorrespondentId(),
                    root -> root.join(CorrespondentFee_.cpiCorrespondent, JoinType.LEFT).get(CPICorrespondent_.id)));
            }
        }
        return specification;
    }
}
