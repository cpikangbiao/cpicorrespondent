package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.ClubPersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClubPerson.
 */
public interface ClubPersonService {

    /**
     * Save a clubPerson.
     *
     * @param clubPersonDTO the entity to save
     * @return the persisted entity
     */
    ClubPersonDTO save(ClubPersonDTO clubPersonDTO);

    /**
     * Get all the clubPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClubPersonDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clubPerson.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClubPersonDTO findOne(Long id);

    /**
     * Delete the "id" clubPerson.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
