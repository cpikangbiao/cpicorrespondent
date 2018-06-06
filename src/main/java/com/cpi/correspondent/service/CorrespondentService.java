package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Correspondent.
 */
public interface CorrespondentService {

    /**
     * Save a correspondent.
     *
     * @param correspondentDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentDTO save(CorrespondentDTO correspondentDTO);

    /**
     * Get all the correspondents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentDTO findOne(Long id);

    /**
     * Delete the "id" correspondent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
