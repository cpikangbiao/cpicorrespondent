package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CorrespondentType.
 */
public interface CorrespondentTypeService {

    /**
     * Save a correspondentType.
     *
     * @param correspondentTypeDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentTypeDTO save(CorrespondentTypeDTO correspondentTypeDTO);

    /**
     * Get all the correspondentTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondentType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentTypeDTO findOne(Long id);

    /**
     * Delete the "id" correspondentType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
