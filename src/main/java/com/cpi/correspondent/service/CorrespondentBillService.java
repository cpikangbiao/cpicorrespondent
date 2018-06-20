package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CorrespondentBill.
 */
public interface CorrespondentBillService {

    /**
     * Save a correspondentBill.
     *
     * @param correspondentBillDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentBillDTO save(CorrespondentBillDTO correspondentBillDTO);

    /**
     * Get all the correspondentBills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentBillDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondentBill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentBillDTO findOne(Long id);

    /**
     * Delete the "id" correspondentBill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
