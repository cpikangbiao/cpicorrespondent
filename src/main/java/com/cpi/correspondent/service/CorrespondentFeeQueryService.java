package com.cpi.correspondent.service;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
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
 * Service for executing complex queries for CorrespondentFee entities in the database.
 * The main input is a {@link CorrespondentFeeCriteria} which get's converted to {@link Specifications},
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
     * Return a {@link List} of {@link CorrespondentFeeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentFeeDTO> findByCriteria(CorrespondentFeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentFee> specification = createSpecification(criteria);
        return correspondentFeeMapper.toDto(correspondentFeeRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public List<CorrespondentFeeDTO> findByCpiCorrespondent(Long cpiCorrespondentId) {
        log.debug("find by cpiCorrespondentId : {}", cpiCorrespondentId);
        return correspondentFeeMapper.toDto(correspondentFeeRepository.findByCpiCorrespondentId(cpiCorrespondentId));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentFeeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeDTO> findByCriteria(CorrespondentFeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentFee> specification = createSpecification(criteria);
        final Page<CorrespondentFee> result = correspondentFeeRepository.findAll(specification, page);
        return result.map(correspondentFeeMapper::toDto);
    }

    /**
     * Function to convert CorrespondentFeeCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentFee> createSpecification(CorrespondentFeeCriteria criteria) {
        Specifications<CorrespondentFee> specification = Specifications.where(null);
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
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentFeeTypeId(), CorrespondentFee_.correspondentFeeType, CorrespondentFeeType_.id));
            }
            if (criteria.getCpiCorrespondentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCpiCorrespondentId(), CorrespondentFee_.cpiCorrespondent, CPICorrespondent_.id));
            }
        }
        return specification;
    }

}
