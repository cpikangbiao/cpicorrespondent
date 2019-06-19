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

import com.cpi.correspondent.domain.Club;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.ClubRepository;
import com.cpi.correspondent.service.dto.ClubCriteria;
import com.cpi.correspondent.service.dto.ClubDTO;
import com.cpi.correspondent.service.mapper.ClubMapper;

/**
 * Service for executing complex queries for {@link Club} entities in the database.
 * The main input is a {@link ClubCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClubDTO} or a {@link Page} of {@link ClubDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClubQueryService extends QueryService<Club> {

    private final Logger log = LoggerFactory.getLogger(ClubQueryService.class);

    private final ClubRepository clubRepository;

    private final ClubMapper clubMapper;

    public ClubQueryService(ClubRepository clubRepository, ClubMapper clubMapper) {
        this.clubRepository = clubRepository;
        this.clubMapper = clubMapper;
    }

    /**
     * Return a {@link List} of {@link ClubDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClubDTO> findByCriteria(ClubCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Club> specification = createSpecification(criteria);
        return clubMapper.toDto(clubRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClubDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClubDTO> findByCriteria(ClubCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Club> specification = createSpecification(criteria);
        return clubRepository.findAll(specification, page)
            .map(clubMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClubCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Club> specification = createSpecification(criteria);
        return clubRepository.count(specification);
    }

    /**
     * Function to convert ClubCriteria to a {@link Specification}.
     */
    private Specification<Club> createSpecification(ClubCriteria criteria) {
        Specification<Club> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Club_.id));
            }
            if (criteria.getClubName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClubName(), Club_.clubName));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Club_.url));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Club_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Club_.phone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Club_.fax));
            }
            if (criteria.getMobilePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobilePhone(), Club_.mobilePhone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Club_.address));
            }
            if (criteria.getZip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZip(), Club_.zip));
            }
        }
        return specification;
    }
}
