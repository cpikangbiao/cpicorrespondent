package com.cpi.correspondent.service;

import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.correspondent.domain.BillFinanceType}.
 */
public interface BillFinanceTypeService {

    /**
     * Save a billFinanceType.
     *
     * @param billFinanceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BillFinanceTypeDTO save(BillFinanceTypeDTO billFinanceTypeDTO);

    /**
     * Get all the billFinanceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BillFinanceTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" billFinanceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillFinanceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" billFinanceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
