package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentBillStatusService;
import com.cpi.correspondent.domain.CorrespondentBillStatus;
import com.cpi.correspondent.repository.CorrespondentBillStatusRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CorrespondentBillStatus}.
 */
@Service
@Transactional
public class CorrespondentBillStatusServiceImpl implements CorrespondentBillStatusService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusServiceImpl.class);

    private final CorrespondentBillStatusRepository correspondentBillStatusRepository;

    private final CorrespondentBillStatusMapper correspondentBillStatusMapper;

    public CorrespondentBillStatusServiceImpl(CorrespondentBillStatusRepository correspondentBillStatusRepository, CorrespondentBillStatusMapper correspondentBillStatusMapper) {
        this.correspondentBillStatusRepository = correspondentBillStatusRepository;
        this.correspondentBillStatusMapper = correspondentBillStatusMapper;
    }

    /**
     * Save a correspondentBillStatus.
     *
     * @param correspondentBillStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CorrespondentBillStatusDTO save(CorrespondentBillStatusDTO correspondentBillStatusDTO) {
        log.debug("Request to save CorrespondentBillStatus : {}", correspondentBillStatusDTO);
        CorrespondentBillStatus correspondentBillStatus = correspondentBillStatusMapper.toEntity(correspondentBillStatusDTO);
        correspondentBillStatus = correspondentBillStatusRepository.save(correspondentBillStatus);
        return correspondentBillStatusMapper.toDto(correspondentBillStatus);
    }

    /**
     * Get all the correspondentBillStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentBillStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentBillStatuses");
        return correspondentBillStatusRepository.findAll(pageable)
            .map(correspondentBillStatusMapper::toDto);
    }


    /**
     * Get one correspondentBillStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentBillStatusDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentBillStatus : {}", id);
        return correspondentBillStatusRepository.findById(id)
            .map(correspondentBillStatusMapper::toDto);
    }

    /**
     * Delete the correspondentBillStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentBillStatus : {}", id);
        correspondentBillStatusRepository.deleteById(id);
    }
}
