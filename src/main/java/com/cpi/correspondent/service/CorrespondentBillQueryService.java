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

import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentBillRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillCriteria;

import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillMapper;

/**
 * Service for executing complex queries for CorrespondentBill entities in the database.
 * The main input is a {@link CorrespondentBillCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentBillDTO} or a {@link Page} of {@link CorrespondentBillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentBillQueryService extends QueryService<CorrespondentBill> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillQueryService.class);


    private final CorrespondentBillRepository correspondentBillRepository;

    private final CorrespondentBillMapper correspondentBillMapper;

    public CorrespondentBillQueryService(CorrespondentBillRepository correspondentBillRepository, CorrespondentBillMapper correspondentBillMapper) {
        this.correspondentBillRepository = correspondentBillRepository;
        this.correspondentBillMapper = correspondentBillMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentBillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentBillDTO> findByCriteria(CorrespondentBillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentBill> specification = createSpecification(criteria);
        return correspondentBillMapper.toDto(correspondentBillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentBillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentBillDTO> findByCriteria(CorrespondentBillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentBill> specification = createSpecification(criteria);
        final Page<CorrespondentBill> result = correspondentBillRepository.findAll(specification, page);
        return result.map(correspondentBillMapper::toDto);
    }

    /**
     * Function to convert CorrespondentBillCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentBill> createSpecification(CorrespondentBillCriteria criteria) {
        Specifications<CorrespondentBill> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentBill_.id));
            }
            if (criteria.getNumberId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberId(), CorrespondentBill_.numberId));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), CorrespondentBill_.year));
            }
            if (criteria.getCorrespondentBillCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentBillCode(), CorrespondentBill_.correspondentBillCode));
            }
            if (criteria.getCorrespondentBillDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCorrespondentBillDate(), CorrespondentBill_.correspondentBillDate));
            }
            if (criteria.getReceiver() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiver(), CorrespondentBill_.receiver));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), CorrespondentBill_.dueDate));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CorrespondentBill_.amount));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrency(), CorrespondentBill_.currency));
            }
            if (criteria.getCurrencyRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrencyRate(), CorrespondentBill_.currencyRate));
            }
            if (criteria.getExchangeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExchangeDate(), CorrespondentBill_.exchangeDate));
            }
            if (criteria.getExchangeCurrency() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExchangeCurrency(), CorrespondentBill_.exchangeCurrency));
            }
            if (criteria.getExchangeRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExchangeRate(), CorrespondentBill_.exchangeRate));
            }
            if (criteria.getExchangeAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExchangeAmount(), CorrespondentBill_.exchangeAmount));
            }
            if (criteria.getCpiCorrespondentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCpiCorrespondentId(), CorrespondentBill_.cpiCorrespondent, CPICorrespondent_.id));
            }
            if (criteria.getBillFinanceTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBillFinanceTypeId(), CorrespondentBill_.billFinanceType, BillFinanceType_.id));
            }
        }
        return specification;
    }

}
