package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.BillFinanceTypeService;
import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.repository.BillFinanceTypeRepository;
import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;
import com.cpi.correspondent.service.mapper.BillFinanceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BillFinanceType.
 */
@Service
@Transactional
public class BillFinanceTypeServiceImpl implements BillFinanceTypeService {

    private final Logger log = LoggerFactory.getLogger(BillFinanceTypeServiceImpl.class);

    private final BillFinanceTypeRepository billFinanceTypeRepository;

    private final BillFinanceTypeMapper billFinanceTypeMapper;

    public BillFinanceTypeServiceImpl(BillFinanceTypeRepository billFinanceTypeRepository, BillFinanceTypeMapper billFinanceTypeMapper) {
        this.billFinanceTypeRepository = billFinanceTypeRepository;
        this.billFinanceTypeMapper = billFinanceTypeMapper;
    }

    /**
     * Save a billFinanceType.
     *
     * @param billFinanceTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BillFinanceTypeDTO save(BillFinanceTypeDTO billFinanceTypeDTO) {
        log.debug("Request to save BillFinanceType : {}", billFinanceTypeDTO);
        BillFinanceType billFinanceType = billFinanceTypeMapper.toEntity(billFinanceTypeDTO);
        billFinanceType = billFinanceTypeRepository.save(billFinanceType);
        return billFinanceTypeMapper.toDto(billFinanceType);
    }

    /**
     * Get all the billFinanceTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BillFinanceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BillFinanceTypes");
        return billFinanceTypeRepository.findAll(pageable)
            .map(billFinanceTypeMapper::toDto);
    }

    /**
     * Get one billFinanceType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BillFinanceTypeDTO findOne(Long id) {
        log.debug("Request to get BillFinanceType : {}", id);
        BillFinanceType billFinanceType = billFinanceTypeRepository.findOne(id);
        return billFinanceTypeMapper.toDto(billFinanceType);
    }

    /**
     * Delete the billFinanceType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillFinanceType : {}", id);
        billFinanceTypeRepository.delete(id);
    }
}
