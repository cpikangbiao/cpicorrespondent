package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CPICorrespondent.
 */
public interface CPICorrespondentService {

    /**
     * Save a cPICorrespondent.
     *
     * @param cPICorrespondentDTO the entity to save
     * @return the persisted entity
     */
    CPICorrespondentDTO save(CPICorrespondentDTO cPICorrespondentDTO);

    /**
     * Get all the cPICorrespondents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CPICorrespondentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cPICorrespondent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CPICorrespondentDTO findOne(Long id);

    /**
     * Delete the "id" cPICorrespondent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
