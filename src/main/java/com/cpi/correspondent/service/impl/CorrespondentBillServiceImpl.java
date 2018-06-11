package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentBillService;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.repository.CorrespondentBillRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CorrespondentBill.
 */
@Service
@Transactional
public class CorrespondentBillServiceImpl implements CorrespondentBillService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillServiceImpl.class);

    private final CorrespondentBillRepository correspondentBillRepository;

    private final CorrespondentBillMapper correspondentBillMapper;

    public CorrespondentBillServiceImpl(CorrespondentBillRepository correspondentBillRepository, CorrespondentBillMapper correspondentBillMapper) {
        this.correspondentBillRepository = correspondentBillRepository;
        this.correspondentBillMapper = correspondentBillMapper;
    }

    /**
     * Save a correspondentBill.
     *
     * @param correspondentBillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentBillDTO save(CorrespondentBillDTO correspondentBillDTO) {
        log.debug("Request to save CorrespondentBill : {}", correspondentBillDTO);
        CorrespondentBill correspondentBill = correspondentBillMapper.toEntity(correspondentBillDTO);
        correspondentBill = correspondentBillRepository.save(correspondentBill);
        return correspondentBillMapper.toDto(correspondentBill);
    }

    /**
     * Get all the correspondentBills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentBillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentBills");
        return correspondentBillRepository.findAll(pageable)
            .map(correspondentBillMapper::toDto);
    }

    /**
     * Get one correspondentBill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CorrespondentBillDTO findOne(Long id) {
        log.debug("Request to get CorrespondentBill : {}", id);
        CorrespondentBill correspondentBill = correspondentBillRepository.findOne(id);
        return correspondentBillMapper.toDto(correspondentBill);
    }

    /**
     * Delete the correspondentBill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentBill : {}", id);
        correspondentBillRepository.delete(id);
    }
}
