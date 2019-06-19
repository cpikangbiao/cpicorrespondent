package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CorrespondentDocsService;
import com.cpi.correspondent.domain.CorrespondentDocs;
import com.cpi.correspondent.repository.CorrespondentDocsRepository;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import com.cpi.correspondent.service.mapper.CorrespondentDocsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CorrespondentDocs}.
 */
@Service
@Transactional
public class CorrespondentDocsServiceImpl implements CorrespondentDocsService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentDocsServiceImpl.class);

    private final CorrespondentDocsRepository correspondentDocsRepository;

    private final CorrespondentDocsMapper correspondentDocsMapper;

    public CorrespondentDocsServiceImpl(CorrespondentDocsRepository correspondentDocsRepository, CorrespondentDocsMapper correspondentDocsMapper) {
        this.correspondentDocsRepository = correspondentDocsRepository;
        this.correspondentDocsMapper = correspondentDocsMapper;
    }

    /**
     * Save a correspondentDocs.
     *
     * @param correspondentDocsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CorrespondentDocsDTO save(CorrespondentDocsDTO correspondentDocsDTO) {
        log.debug("Request to save CorrespondentDocs : {}", correspondentDocsDTO);
        CorrespondentDocs correspondentDocs = correspondentDocsMapper.toEntity(correspondentDocsDTO);
        correspondentDocs = correspondentDocsRepository.save(correspondentDocs);
        return correspondentDocsMapper.toDto(correspondentDocs);
    }

    /**
     * Get all the correspondentDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentDocsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentDocs");
        return correspondentDocsRepository.findAll(pageable)
            .map(correspondentDocsMapper::toDto);
    }


    /**
     * Get one correspondentDocs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentDocsDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentDocs : {}", id);
        return correspondentDocsRepository.findById(id)
            .map(correspondentDocsMapper::toDto);
    }

    /**
     * Delete the correspondentDocs by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentDocs : {}", id);
        correspondentDocsRepository.deleteById(id);
    }
}
