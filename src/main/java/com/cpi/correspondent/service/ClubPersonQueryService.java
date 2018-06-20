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

import com.cpi.correspondent.domain.ClubPerson;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.ClubPersonRepository;
import com.cpi.correspondent.service.dto.ClubPersonCriteria;

import com.cpi.correspondent.service.dto.ClubPersonDTO;
import com.cpi.correspondent.service.mapper.ClubPersonMapper;

/**
 * Service for executing complex queries for ClubPerson entities in the database.
 * The main input is a {@link ClubPersonCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClubPersonDTO} or a {@link Page} of {@link ClubPersonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClubPersonQueryService extends QueryService<ClubPerson> {

    private final Logger log = LoggerFactory.getLogger(ClubPersonQueryService.class);


    private final ClubPersonRepository clubPersonRepository;

    private final ClubPersonMapper clubPersonMapper;

    public ClubPersonQueryService(ClubPersonRepository clubPersonRepository, ClubPersonMapper clubPersonMapper) {
        this.clubPersonRepository = clubPersonRepository;
        this.clubPersonMapper = clubPersonMapper;
    }

    /**
     * Return a {@link List} of {@link ClubPersonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClubPersonDTO> findByCriteria(ClubPersonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClubPerson> specification = createSpecification(criteria);
        return clubPersonMapper.toDto(clubPersonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClubPersonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClubPersonDTO> findByCriteria(ClubPersonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClubPerson> specification = createSpecification(criteria);
        final Page<ClubPerson> result = clubPersonRepository.findAll(specification, page);
        return result.map(clubPersonMapper::toDto);
    }

    /**
     * Function to convert ClubPersonCriteria to a {@link Specifications}
     */
    private Specifications<ClubPerson> createSpecification(ClubPersonCriteria criteria) {
        Specifications<ClubPerson> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClubPerson_.id));
            }
            if (criteria.getClubPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClubPersonName(), ClubPerson_.clubPersonName));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), ClubPerson_.url));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ClubPerson_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), ClubPerson_.phone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), ClubPerson_.fax));
            }
            if (criteria.getMobilePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobilePhone(), ClubPerson_.mobilePhone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ClubPerson_.address));
            }
            if (criteria.getZip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZip(), ClubPerson_.zip));
            }
            if (criteria.getClubId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClubId(), ClubPerson_.club, Club_.id));
            }
        }
        return specification;
    }

}
