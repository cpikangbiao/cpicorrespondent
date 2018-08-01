package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CorrespondentDocs.
 */
public interface CorrespondentDocsService {

    /**
     * Save a correspondentDocs.
     *
     * @param correspondentDocsDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentDocsDTO save(CorrespondentDocsDTO correspondentDocsDTO);

    /**
     * Get all the correspondentDocs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentDocsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondentDocs.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentDocsDTO findOne(Long id);

    /**
     * Delete the "id" correspondentDocs.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
