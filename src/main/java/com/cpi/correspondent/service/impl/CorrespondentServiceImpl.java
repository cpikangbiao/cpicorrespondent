package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentService;
import com.cpi.correspondent.domain.Correspondent;
import com.cpi.correspondent.repository.CorrespondentRepository;
import com.cpi.correspondent.service.dto.CorrespondentDTO;
import com.cpi.correspondent.service.mapper.CorrespondentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Correspondent.
 */
@Service
@Transactional
public class CorrespondentServiceImpl implements CorrespondentService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentServiceImpl.class);

    private final CorrespondentRepository correspondentRepository;

    private final CorrespondentMapper correspondentMapper;

    public CorrespondentServiceImpl(CorrespondentRepository correspondentRepository, CorrespondentMapper correspondentMapper) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentMapper = correspondentMapper;
    }

    /**
     * Save a correspondent.
     *
     * @param correspondentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentDTO save(CorrespondentDTO correspondentDTO) {
        log.debug("Request to save Correspondent : {}", correspondentDTO);
        Correspondent correspondent = correspondentMapper.toEntity(correspondentDTO);
        correspondent = correspondentRepository.save(correspondent);
        return correspondentMapper.toDto(correspondent);
    }

    /**
     * Get all the correspondents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Correspondents");
        return correspondentRepository.findAll(pageable)
            .map(correspondentMapper::toDto);
    }

    /**
     * Get one correspondent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CorrespondentDTO findOne(Long id) {
        log.debug("Request to get Correspondent : {}", id);
        Correspondent correspondent = correspondentRepository.findOne(id);
        return correspondentMapper.toDto(correspondent);
    }

    /**
     * Delete the correspondent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Correspondent : {}", id);
        correspondentRepository.delete(id);
    }
}
