package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CorrespondentBillStatusLog.
 */
public interface CorrespondentBillStatusLogService {

    /**
     * Save a correspondentBillStatusLog.
     *
     * @param correspondentBillStatusLogDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentBillStatusLogDTO save(CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO);

    /**
     * Get all the correspondentBillStatusLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentBillStatusLogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondentBillStatusLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentBillStatusLogDTO findOne(Long id);

    /**
     * Delete the "id" correspondentBillStatusLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
