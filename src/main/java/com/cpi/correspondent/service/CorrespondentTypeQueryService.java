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

import com.cpi.correspondent.domain.CorrespondentType;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentTypeRepository;
import com.cpi.correspondent.service.dto.CorrespondentTypeCriteria;

import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentTypeMapper;

/**
 * Service for executing complex queries for CorrespondentType entities in the database.
 * The main input is a {@link CorrespondentTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentTypeDTO} or a {@link Page} of {@link CorrespondentTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentTypeQueryService extends QueryService<CorrespondentType> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentTypeQueryService.class);


    private final CorrespondentTypeRepository correspondentTypeRepository;

    private final CorrespondentTypeMapper correspondentTypeMapper;

    public CorrespondentTypeQueryService(CorrespondentTypeRepository correspondentTypeRepository, CorrespondentTypeMapper correspondentTypeMapper) {
        this.correspondentTypeRepository = correspondentTypeRepository;
        this.correspondentTypeMapper = correspondentTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentTypeDTO> findByCriteria(CorrespondentTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentType> specification = createSpecification(criteria);
        return correspondentTypeMapper.toDto(correspondentTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentTypeDTO> findByCriteria(CorrespondentTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentType> specification = createSpecification(criteria);
        final Page<CorrespondentType> result = correspondentTypeRepository.findAll(specification, page);
        return result.map(correspondentTypeMapper::toDto);
    }

    /**
     * Function to convert CorrespondentTypeCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentType> createSpecification(CorrespondentTypeCriteria criteria) {
        Specifications<CorrespondentType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentType_.id));
            }
            if (criteria.getCorrespondentTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentTypeName(), CorrespondentType_.correspondentTypeName));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), CorrespondentType_.sortNum));
            }
        }
        return specification;
    }

}
