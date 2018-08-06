package com.cpi.correspondent.service;


import java.util.List;

import com.cpi.correspondent.repository.other.YearCountStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CPICorrespondentRepository;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;

import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.mapper.CPICorrespondentMapper;

/**
 * Service for executing complex queries for CPICorrespondent entities in the database.
 * The main input is a {@link CPICorrespondentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPICorrespondentDTO} or a {@link Page} of {@link CPICorrespondentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPICorrespondentQueryService extends QueryService<CPICorrespondent> {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentQueryService.class);


    private final CPICorrespondentRepository cPICorrespondentRepository;

    private final CPICorrespondentMapper cPICorrespondentMapper;

    public CPICorrespondentQueryService(CPICorrespondentRepository cPICorrespondentRepository, CPICorrespondentMapper cPICorrespondentMapper) {
        this.cPICorrespondentRepository = cPICorrespondentRepository;
        this.cPICorrespondentMapper = cPICorrespondentMapper;
    }

    /**
     * Return a {@link List} of {@link CPICorrespondentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPICorrespondentDTO> findByCriteria(CPICorrespondentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CPICorrespondent> specification = createSpecification(criteria);
        return cPICorrespondentMapper.toDto(cPICorrespondentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPICorrespondentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPICorrespondentDTO> findByCriteria(CPICorrespondentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CPICorrespondent> specification = createSpecification(criteria);
        final Page<CPICorrespondent> result = cPICorrespondentRepository.findAll(specification, page);
        return result.map(cPICorrespondentMapper::toDto);
    }

    /**
     * Function to convert CPICorrespondentCriteria to a {@link Specifications}
     */
    private Specifications<CPICorrespondent> createSpecification(CPICorrespondentCriteria criteria) {
        Specifications<CPICorrespondent> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CPICorrespondent_.id));
            }
            if (criteria.getCorrespondentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentCode(), CPICorrespondent_.correspondentCode));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), CPICorrespondent_.year));
            }
            if (criteria.getVesselName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselName(), CPICorrespondent_.vesselName));
            }
            if (criteria.getClientRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientRef(), CPICorrespondent_.clientRef));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), CPICorrespondent_.keyWord));
            }
            if (criteria.getRegisterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterDate(), CPICorrespondent_.registerDate));
            }
            if (criteria.getCaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCaseDate(), CPICorrespondent_.caseDate));
            }
            if (criteria.getHandlerUser() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHandlerUser(), CPICorrespondent_.handlerUser));
            }
            if (criteria.getCorrespondentTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentTypeId(), CPICorrespondent_.correspondentType, CorrespondentType_.id));
            }
            if (criteria.getClubId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClubId(), CPICorrespondent_.club, Club_.id));
            }
            if (criteria.getClubPersonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClubPersonId(), CPICorrespondent_.clubPerson, ClubPerson_.id));
            }
        }
        return specification;
    }

    @Transactional(readOnly = true)
    public List<YearCountStatistics> findYearCountStatistics() {
        log.debug("find by YearCountStatistics ");
        return cPICorrespondentRepository.findYearStatsCount();
    }

}
