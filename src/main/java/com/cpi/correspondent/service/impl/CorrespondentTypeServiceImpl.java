package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentTypeService;
import com.cpi.correspondent.domain.CorrespondentType;
import com.cpi.correspondent.repository.CorrespondentTypeRepository;
import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CorrespondentType.
 */
@Service
@Transactional
public class CorrespondentTypeServiceImpl implements CorrespondentTypeService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentTypeServiceImpl.class);

    private final CorrespondentTypeRepository correspondentTypeRepository;

    private final CorrespondentTypeMapper correspondentTypeMapper;

    public CorrespondentTypeServiceImpl(CorrespondentTypeRepository correspondentTypeRepository, CorrespondentTypeMapper correspondentTypeMapper) {
        this.correspondentTypeRepository = correspondentTypeRepository;
        this.correspondentTypeMapper = correspondentTypeMapper;
    }

    /**
     * Save a correspondentType.
     *
     * @param correspondentTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentTypeDTO save(CorrespondentTypeDTO correspondentTypeDTO) {
        log.debug("Request to save CorrespondentType : {}", correspondentTypeDTO);
        CorrespondentType correspondentType = correspondentTypeMapper.toEntity(correspondentTypeDTO);
        correspondentType = correspondentTypeRepository.save(correspondentType);
        return correspondentTypeMapper.toDto(correspondentType);
    }

    /**
     * Get all the correspondentTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentTypes");
        return correspondentTypeRepository.findAll(pageable)
            .map(correspondentTypeMapper::toDto);
    }

    /**
     * Get one correspondentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CorrespondentTypeDTO findOne(Long id) {
        log.debug("Request to get CorrespondentType : {}", id);
        CorrespondentType correspondentType = correspondentTypeRepository.findOne(id);
        return correspondentTypeMapper.toDto(correspondentType);
    }

    /**
     * Delete the correspondentType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentType : {}", id);
        correspondentTypeRepository.delete(id);
    }
}
