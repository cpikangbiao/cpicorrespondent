package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentFeeAndBillService;
import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeAndBillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CorrespondentFeeAndBill}.
 */
@Service
@Transactional
public class CorrespondentFeeAndBillServiceImpl implements CorrespondentFeeAndBillService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeAndBillServiceImpl.class);

    private final CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    private final CorrespondentFeeAndBillMapper correspondentFeeAndBillMapper;

    public CorrespondentFeeAndBillServiceImpl(CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository, CorrespondentFeeAndBillMapper correspondentFeeAndBillMapper) {
        this.correspondentFeeAndBillRepository = correspondentFeeAndBillRepository;
        this.correspondentFeeAndBillMapper = correspondentFeeAndBillMapper;
    }

    /**
     * Save a correspondentFeeAndBill.
     *
     * @param correspondentFeeAndBillDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CorrespondentFeeAndBillDTO save(CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO) {
        log.debug("Request to save CorrespondentFeeAndBill : {}", correspondentFeeAndBillDTO);
        CorrespondentFeeAndBill correspondentFeeAndBill = correspondentFeeAndBillMapper.toEntity(correspondentFeeAndBillDTO);
        correspondentFeeAndBill = correspondentFeeAndBillRepository.save(correspondentFeeAndBill);
        return correspondentFeeAndBillMapper.toDto(correspondentFeeAndBill);
    }

    /**
     * Get all the correspondentFeeAndBills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeAndBillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentFeeAndBills");
        return correspondentFeeAndBillRepository.findAll(pageable)
            .map(correspondentFeeAndBillMapper::toDto);
    }


    /**
     * Get one correspondentFeeAndBill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentFeeAndBillDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentFeeAndBill : {}", id);
        return correspondentFeeAndBillRepository.findById(id)
            .map(correspondentFeeAndBillMapper::toDto);
    }

    /**
     * Delete the correspondentFeeAndBill by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentFeeAndBill : {}", id);
        correspondentFeeAndBillRepository.deleteById(id);
    }
}
