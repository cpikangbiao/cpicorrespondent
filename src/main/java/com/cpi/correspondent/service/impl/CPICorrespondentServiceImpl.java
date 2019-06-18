package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CPICorrespondentService;
import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.repository.CPICorrespondentRepository;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.mapper.CPICorrespondentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPICorrespondent}.
 */
@Service
@Transactional
public class CPICorrespondentServiceImpl implements CPICorrespondentService {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentServiceImpl.class);

    private final CPICorrespondentRepository cPICorrespondentRepository;

    private final CPICorrespondentMapper cPICorrespondentMapper;

    public CPICorrespondentServiceImpl(CPICorrespondentRepository cPICorrespondentRepository, CPICorrespondentMapper cPICorrespondentMapper) {
        this.cPICorrespondentRepository = cPICorrespondentRepository;
        this.cPICorrespondentMapper = cPICorrespondentMapper;
    }

    /**
     * Save a cPICorrespondent.
     *
     * @param cPICorrespondentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CPICorrespondentDTO save(CPICorrespondentDTO cPICorrespondentDTO) {
        log.debug("Request to save CPICorrespondent : {}", cPICorrespondentDTO);
        CPICorrespondent cPICorrespondent = cPICorrespondentMapper.toEntity(cPICorrespondentDTO);
        cPICorrespondent = cPICorrespondentRepository.save(cPICorrespondent);
        return cPICorrespondentMapper.toDto(cPICorrespondent);
    }

    /**
     * Get all the cPICorrespondents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CPICorrespondentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPICorrespondents");
        return cPICorrespondentRepository.findAll(pageable)
            .map(cPICorrespondentMapper::toDto);
    }


    /**
     * Get one cPICorrespondent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CPICorrespondentDTO> findOne(Long id) {
        log.debug("Request to get CPICorrespondent : {}", id);
        return cPICorrespondentRepository.findById(id)
            .map(cPICorrespondentMapper::toDto);
    }

    /**
     * Delete the cPICorrespondent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CPICorrespondent : {}", id);
        cPICorrespondentRepository.deleteById(id);
    }
}
