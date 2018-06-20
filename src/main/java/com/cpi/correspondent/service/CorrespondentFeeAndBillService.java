package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CorrespondentFeeAndBill.
 */
public interface CorrespondentFeeAndBillService {

    /**
     * Save a correspondentFeeAndBill.
     *
     * @param correspondentFeeAndBillDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentFeeAndBillDTO save(CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO);

    /**
     * Get all the correspondentFeeAndBills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentFeeAndBillDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondentFeeAndBill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentFeeAndBillDTO findOne(Long id);

    /**
     * Delete the "id" correspondentFeeAndBill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
