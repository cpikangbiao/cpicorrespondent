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

import com.cpi.correspondent.domain.CorrespondentDocs;
import com.cpi.correspondent.domain.*; // for static metamodels
import com.cpi.correspondent.repository.CorrespondentDocsRepository;
import com.cpi.correspondent.service.dto.CorrespondentDocsCriteria;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import com.cpi.correspondent.service.mapper.CorrespondentDocsMapper;

/**
 * Service for executing complex queries for {@link CorrespondentDocs} entities in the database.
 * The main input is a {@link CorrespondentDocsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentDocsDTO} or a {@link Page} of {@link CorrespondentDocsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentDocsQueryService extends QueryService<CorrespondentDocs> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentDocsQueryService.class);

    private final CorrespondentDocsRepository correspondentDocsRepository;

    private final CorrespondentDocsMapper correspondentDocsMapper;

    public CorrespondentDocsQueryService(CorrespondentDocsRepository correspondentDocsRepository, CorrespondentDocsMapper correspondentDocsMapper) {
        this.correspondentDocsRepository = correspondentDocsRepository;
        this.correspondentDocsMapper = correspondentDocsMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentDocsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentDocsDTO> findByCriteria(CorrespondentDocsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CorrespondentDocs> specification = createSpecification(criteria);
        return correspondentDocsMapper.toDto(correspondentDocsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentDocsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentDocsDTO> findByCriteria(CorrespondentDocsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CorrespondentDocs> specification = createSpecification(criteria);
        return correspondentDocsRepository.findAll(specification, page)
            .map(correspondentDocsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorrespondentDocsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CorrespondentDocs> specification = createSpecification(criteria);
        return correspondentDocsRepository.count(specification);
    }

    /**
     * Function to convert CorrespondentDocsCriteria to a {@link Specification}.
     */
    private Specification<CorrespondentDocs> createSpecification(CorrespondentDocsCriteria criteria) {
        Specification<CorrespondentDocs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentDocs_.id));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), CorrespondentDocs_.documentName));
            }
            if (criteria.getUploadDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadDate(), CorrespondentDocs_.uploadDate));
            }
            if (criteria.getCpiCorrespondentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCpiCorrespondentId(),
                    root -> root.join(CorrespondentDocs_.cpiCorrespondent, JoinType.LEFT).get(CPICorrespondent_.id)));
            }
        }
        return specification;
    }
}
