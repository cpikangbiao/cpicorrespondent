package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentFeeTypeService;
import com.cpi.correspondent.domain.CorrespondentFeeType;
import com.cpi.correspondent.repository.CorrespondentFeeTypeRepository;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CorrespondentFeeType.
 */
@Service
@Transactional
public class CorrespondentFeeTypeServiceImpl implements CorrespondentFeeTypeService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeTypeServiceImpl.class);

    private final CorrespondentFeeTypeRepository correspondentFeeTypeRepository;

    private final CorrespondentFeeTypeMapper correspondentFeeTypeMapper;

    public CorrespondentFeeTypeServiceImpl(CorrespondentFeeTypeRepository correspondentFeeTypeRepository, CorrespondentFeeTypeMapper correspondentFeeTypeMapper) {
        this.correspondentFeeTypeRepository = correspondentFeeTypeRepository;
        this.correspondentFeeTypeMapper = correspondentFeeTypeMapper;
    }

    /**
     * Save a correspondentFeeType.
     *
     * @param correspondentFeeTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentFeeTypeDTO save(CorrespondentFeeTypeDTO correspondentFeeTypeDTO) {
        log.debug("Request to save CorrespondentFeeType : {}", correspondentFeeTypeDTO);
        CorrespondentFeeType correspondentFeeType = correspondentFeeTypeMapper.toEntity(correspondentFeeTypeDTO);
        correspondentFeeType = correspondentFeeTypeRepository.save(correspondentFeeType);
        return correspondentFeeTypeMapper.toDto(correspondentFeeType);
    }

    /**
     * Get all the correspondentFeeTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentFeeTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentFeeTypes");
        return correspondentFeeTypeRepository.findAll(pageable)
            .map(correspondentFeeTypeMapper::toDto);
    }

    /**
     * Get one correspondentFeeType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CorrespondentFeeTypeDTO findOne(Long id) {
        log.debug("Request to get CorrespondentFeeType : {}", id);
        CorrespondentFeeType correspondentFeeType = correspondentFeeTypeRepository.findOne(id);
        return correspondentFeeTypeMapper.toDto(correspondentFeeType);
    }

    /**
     * Delete the correspondentFeeType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentFeeType : {}", id);
        correspondentFeeTypeRepository.delete(id);
    }
}
