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

import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.BillFinanceTypeRepository;
import com.cpi.correspondent.service.dto.BillFinanceTypeCriteria;
import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;
import com.cpi.correspondent.service.mapper.BillFinanceTypeMapper;

/**
 * Service for executing complex queries for {@link BillFinanceType} entities in the database.
 * The main input is a {@link BillFinanceTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BillFinanceTypeDTO} or a {@link Page} of {@link BillFinanceTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillFinanceTypeQueryService extends QueryService<BillFinanceType> {

    private final Logger log = LoggerFactory.getLogger(BillFinanceTypeQueryService.class);

    private final BillFinanceTypeRepository billFinanceTypeRepository;

    private final BillFinanceTypeMapper billFinanceTypeMapper;

    public BillFinanceTypeQueryService(BillFinanceTypeRepository billFinanceTypeRepository, BillFinanceTypeMapper billFinanceTypeMapper) {
        this.billFinanceTypeRepository = billFinanceTypeRepository;
        this.billFinanceTypeMapper = billFinanceTypeMapper;
    }

    /**
     * Return a {@link List} of {@link BillFinanceTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BillFinanceTypeDTO> findByCriteria(BillFinanceTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BillFinanceType> specification = createSpecification(criteria);
        return billFinanceTypeMapper.toDto(billFinanceTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BillFinanceTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BillFinanceTypeDTO> findByCriteria(BillFinanceTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BillFinanceType> specification = createSpecification(criteria);
        return billFinanceTypeRepository.findAll(specification, page)
            .map(billFinanceTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BillFinanceTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BillFinanceType> specification = createSpecification(criteria);
        return billFinanceTypeRepository.count(specification);
    }

    /**
     * Function to convert BillFinanceTypeCriteria to a {@link Specification}.
     */
    private Specification<BillFinanceType> createSpecification(BillFinanceTypeCriteria criteria) {
        Specification<BillFinanceType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BillFinanceType_.id));
            }
            if (criteria.getBillFinanceTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillFinanceTypeName(), BillFinanceType_.billFinanceTypeName));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), BillFinanceType_.sortNum));
            }
        }
        return specification;
    }
}
