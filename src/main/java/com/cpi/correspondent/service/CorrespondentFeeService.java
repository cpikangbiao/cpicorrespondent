package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.correspondent.domain.CorrespondentFee}.
 */
public interface CorrespondentFeeService {

    /**
     * Save a correspondentFee.
     *
     * @param correspondentFeeDTO the entity to save.
     * @return the persisted entity.
     */
    CorrespondentFeeDTO save(CorrespondentFeeDTO correspondentFeeDTO);

    /**
     * Get all the correspondentFees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CorrespondentFeeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" correspondentFee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CorrespondentFeeDTO> findOne(Long id);

    /**
     * Delete the "id" correspondentFee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
