package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CreditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Credit.
 */
public interface CreditService {

    /**
     * Save a credit.
     *
     * @param creditDTO the entity to save
     * @return the persisted entity
     */
    CreditDTO save(CreditDTO creditDTO);

    /**
     * Get all the credits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CreditDTO> findAll(Pageable pageable);

    /**
     * Get the "id" credit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CreditDTO findOne(Long id);

    /**
     * Delete the "id" credit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
