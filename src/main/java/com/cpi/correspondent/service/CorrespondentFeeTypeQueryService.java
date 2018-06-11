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

import com.cpi.correspondent.domain.CorrespondentFeeType;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentFeeTypeRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeCriteria;

import com.cpi.correspondent.service.dto.CorrespondentFeeTypeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeTypeMapper;

/**
 * Service for executing complex queries for CorrespondentFeeType entities in the database.
 * The main input is a {@link CorrespondentFeeTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentFeeTypeDTO} or a {@link Page} of {@link CorrespondentFeeTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentFeeTypeQueryService extends QueryService<CorrespondentFeeType> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeTypeQueryService.class);


    private final CorrespondentFeeTypeRepository correspondentFeeTypeRepository;

    private final CorrespondentFeeTypeMapper correspondentFeeTypeMapper;

    public CorrespondentFeeTypeQueryService(CorrespondentFeeTypeRepository correspondentFeeTypeRepository, CorrespondentFeeTypeMapper correspondentFeeTypeMapper) {
        this.correspondentFeeTypeRepository = correspondentFeeTypeRepository;
        this.correspondentFeeTypeMapper = correspondentFeeTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentFeeTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentFeeTypeDTO> findByCriteria(CorrespondentFeeTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentFeeType> specification = createSpecification(criteria);
        return correspondentFeeTypeMapper.toDto(correspondentFeeTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentFeeTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeTypeDTO> findByCriteria(CorrespondentFeeTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentFeeType> specification = createSpecification(criteria);
        final Page<CorrespondentFeeType> result = correspondentFeeTypeRepository.findAll(specification, page);
        return result.map(correspondentFeeTypeMapper::toDto);
    }

    /**
     * Function to convert CorrespondentFeeTypeCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentFeeType> createSpecification(CorrespondentFeeTypeCriteria criteria) {
        Specifications<CorrespondentFeeType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentFeeType_.id));
            }
            if (criteria.getCorrespondentFeeTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentFeeTypeName(), CorrespondentFeeType_.correspondentFeeTypeName));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), CorrespondentFeeType_.sortNum));
            }
        }
        return specification;
    }

}
