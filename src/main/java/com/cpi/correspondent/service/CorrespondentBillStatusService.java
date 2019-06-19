package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.correspondent.domain.CorrespondentBillStatus}.
 */
public interface CorrespondentBillStatusService {

    /**
     * Save a correspondentBillStatus.
     *
     * @param correspondentBillStatusDTO the entity to save.
     * @return the persisted entity.
     */
    CorrespondentBillStatusDTO save(CorrespondentBillStatusDTO correspondentBillStatusDTO);

    /**
     * Get all the correspondentBillStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CorrespondentBillStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" correspondentBillStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CorrespondentBillStatusDTO> findOne(Long id);

    /**
     * Delete the "id" correspondentBillStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
