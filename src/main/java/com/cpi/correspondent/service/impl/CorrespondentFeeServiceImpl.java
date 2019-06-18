package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentFeeService;
import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.repository.CorrespondentFeeRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CorrespondentFee}.
 */
@Service
@Transactional
public class CorrespondentFeeServiceImpl implements CorrespondentFeeService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeServiceImpl.class);

    private final CorrespondentFeeRepository correspondentFeeRepository;

    private final CorrespondentFeeMapper correspondentFeeMapper;

    public CorrespondentFeeServiceImpl(CorrespondentFeeRepository correspondentFeeRepository, CorrespondentFeeMapper correspondentFeeMapper) {
        this.correspondentFeeRepository = correspondentFeeRepository;
        this.correspondentFeeMapper = correspondentFeeMapper;
    }

    /**
     * Save a correspondentFee.
     *
     * @param correspondentFeeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CorrespondentFeeDTO save(CorrespondentFeeDTO correspondentFeeDTO) {
        log.debug("Request to save CorrespondentFee : {}", correspondentFeeDTO);
        CorrespondentFee correspondentFee = correspondentFeeMapper.toEntity(correspondentFeeDTO);
        correspondentFee = correspondentFeeRepository.save(correspondentFee);
        return correspondentFeeMapper.toDto(correspondentFee);
    }

    /**
     * Get all the correspondentFees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentFees");
        return correspondentFeeRepository.findAll(pageable)
            .map(correspondentFeeMapper::toDto);
    }


    /**
     * Get one correspondentFee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentFeeDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentFee : {}", id);
        return correspondentFeeRepository.findById(id)
            .map(correspondentFeeMapper::toDto);
    }

    /**
     * Delete the correspondentFee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentFee : {}", id);
        correspondentFeeRepository.deleteById(id);
    }
}
