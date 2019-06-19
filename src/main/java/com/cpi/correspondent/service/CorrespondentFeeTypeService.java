package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentFeeTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.correspondent.domain.CorrespondentFeeType}.
 */
public interface CorrespondentFeeTypeService {

    /**
     * Save a correspondentFeeType.
     *
     * @param correspondentFeeTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CorrespondentFeeTypeDTO save(CorrespondentFeeTypeDTO correspondentFeeTypeDTO);

    /**
     * Get all the correspondentFeeTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CorrespondentFeeTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" correspondentFeeType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CorrespondentFeeTypeDTO> findOne(Long id);

    /**
     * Delete the "id" correspondentFeeType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
