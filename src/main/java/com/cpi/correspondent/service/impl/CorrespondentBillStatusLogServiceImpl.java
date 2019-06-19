package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentBillStatusLogService;
import com.cpi.correspondent.domain.CorrespondentBillStatusLog;
import com.cpi.correspondent.repository.CorrespondentBillStatusLogRepository;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CorrespondentBillStatusLog}.
 */
@Service
@Transactional
public class CorrespondentBillStatusLogServiceImpl implements CorrespondentBillStatusLogService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusLogServiceImpl.class);

    private final CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository;

    private final CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper;

    public CorrespondentBillStatusLogServiceImpl(CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository, CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper) {
        this.correspondentBillStatusLogRepository = correspondentBillStatusLogRepository;
        this.correspondentBillStatusLogMapper = correspondentBillStatusLogMapper;
    }

    /**
     * Save a correspondentBillStatusLog.
     *
     * @param correspondentBillStatusLogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CorrespondentBillStatusLogDTO save(CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO) {
        log.debug("Request to save CorrespondentBillStatusLog : {}", correspondentBillStatusLogDTO);
        CorrespondentBillStatusLog correspondentBillStatusLog = correspondentBillStatusLogMapper.toEntity(correspondentBillStatusLogDTO);
        correspondentBillStatusLog = correspondentBillStatusLogRepository.save(correspondentBillStatusLog);
        return correspondentBillStatusLogMapper.toDto(correspondentBillStatusLog);
    }

    /**
     * Get all the correspondentBillStatusLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentBillStatusLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentBillStatusLogs");
        return correspondentBillStatusLogRepository.findAll(pageable)
            .map(correspondentBillStatusLogMapper::toDto);
    }


    /**
     * Get one correspondentBillStatusLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentBillStatusLogDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentBillStatusLog : {}", id);
        return correspondentBillStatusLogRepository.findById(id)
            .map(correspondentBillStatusLogMapper::toDto);
    }

    /**
     * Delete the correspondentBillStatusLog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentBillStatusLog : {}", id);
        correspondentBillStatusLogRepository.deleteById(id);
    }
}
