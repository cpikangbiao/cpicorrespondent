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

import com.cpi.correspondent.domain.Correspondent;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentRepository;
import com.cpi.correspondent.service.dto.CorrespondentCriteria;

import com.cpi.correspondent.service.dto.CorrespondentDTO;
import com.cpi.correspondent.service.mapper.CorrespondentMapper;

/**
 * Service for executing complex queries for Correspondent entities in the database.
 * The main input is a {@link CorrespondentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentDTO} or a {@link Page} of {@link CorrespondentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentQueryService extends QueryService<Correspondent> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentQueryService.class);


    private final CorrespondentRepository correspondentRepository;

    private final CorrespondentMapper correspondentMapper;

    public CorrespondentQueryService(CorrespondentRepository correspondentRepository, CorrespondentMapper correspondentMapper) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentMapper = correspondentMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentDTO> findByCriteria(CorrespondentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Correspondent> specification = createSpecification(criteria);
        return correspondentMapper.toDto(correspondentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentDTO> findByCriteria(CorrespondentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Correspondent> specification = createSpecification(criteria);
        final Page<Correspondent> result = correspondentRepository.findAll(specification, page);
        return result.map(correspondentMapper::toDto);
    }

    /**
     * Function to convert CorrespondentCriteria to a {@link Specifications}
     */
    private Specifications<Correspondent> createSpecification(CorrespondentCriteria criteria) {
        Specifications<Correspondent> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Correspondent_.id));
            }
            if (criteria.getCorrespondentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentCode(), Correspondent_.correspondentCode));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), Correspondent_.year));
            }
            if (criteria.getVesselName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselName(), Correspondent_.vesselName));
            }
            if (criteria.getClientRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientRef(), Correspondent_.clientRef));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), Correspondent_.keyWord));
            }
            if (criteria.getRegisterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterDate(), Correspondent_.registerDate));
            }
            if (criteria.getCaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCaseDate(), Correspondent_.caseDate));
            }
            if (criteria.getHandlerUser() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHandlerUser(), Correspondent_.handlerUser));
            }
            if (criteria.getCorrespondentTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentTypeId(), Correspondent_.correspondentType, CorrespondentType_.id));
            }
            if (criteria.getClubId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClubId(), Correspondent_.club, Club_.id));
            }
            if (criteria.getClubPersonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClubPersonId(), Correspondent_.clubPerson, ClubPerson_.id));
            }
        }
        return specification;
    }

}
