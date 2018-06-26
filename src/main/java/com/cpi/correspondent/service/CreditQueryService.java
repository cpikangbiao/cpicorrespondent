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

import com.cpi.correspondent.domain.Credit;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CreditRepository;
import com.cpi.correspondent.service.dto.CreditCriteria;

import com.cpi.correspondent.service.dto.CreditDTO;
import com.cpi.correspondent.service.mapper.CreditMapper;

/**
 * Service for executing complex queries for Credit entities in the database.
 * The main input is a {@link CreditCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CreditDTO} or a {@link Page} of {@link CreditDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CreditQueryService extends QueryService<Credit> {

    private final Logger log = LoggerFactory.getLogger(CreditQueryService.class);


    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    public CreditQueryService(CreditRepository creditRepository, CreditMapper creditMapper) {
        this.creditRepository = creditRepository;
        this.creditMapper = creditMapper;
    }

    /**
     * Return a {@link List} of {@link CreditDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CreditDTO> findByCriteria(CreditCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Credit> specification = createSpecification(criteria);
        return creditMapper.toDto(creditRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CreditDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CreditDTO> findByCriteria(CreditCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Credit> specification = createSpecification(criteria);
        final Page<Credit> result = creditRepository.findAll(specification, page);
        return result.map(creditMapper::toDto);
    }

    /**
     * Function to convert CreditCriteria to a {@link Specifications}
     */
    private Specifications<Credit> createSpecification(CreditCriteria criteria) {
        Specifications<Credit> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Credit_.id));
            }
            if (criteria.getNumberId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberId(), Credit_.numberId));
            }
            if (criteria.getCreditorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditorName(), Credit_.creditorName));
            }
            if (criteria.getCreditorAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditorAddress(), Credit_.creditorAddress));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), Credit_.bankName));
            }
            if (criteria.getBankAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAddress(), Credit_.bankAddress));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), Credit_.accountNo));
            }
            if (criteria.getCorrBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrBankName(), Credit_.corrBankName));
            }
            if (criteria.getCorrBankAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrBankAddress(), Credit_.corrBankAddress));
            }
        }
        return specification;
    }

}
