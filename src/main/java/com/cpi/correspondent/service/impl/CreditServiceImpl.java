package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.CreditService;
import com.cpi.correspondent.domain.Credit;
import com.cpi.correspondent.repository.CreditRepository;
import com.cpi.correspondent.service.dto.CreditDTO;
import com.cpi.correspondent.service.mapper.CreditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Credit}.
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    public CreditServiceImpl(CreditRepository creditRepository, CreditMapper creditMapper) {
        this.creditRepository = creditRepository;
        this.creditMapper = creditMapper;
    }

    /**
     * Save a credit.
     *
     * @param creditDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CreditDTO save(CreditDTO creditDTO) {
        log.debug("Request to save Credit : {}", creditDTO);
        Credit credit = creditMapper.toEntity(creditDTO);
        credit = creditRepository.save(credit);
        return creditMapper.toDto(credit);
    }

    /**
     * Get all the credits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CreditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Credits");
        return creditRepository.findAll(pageable)
            .map(creditMapper::toDto);
    }


    /**
     * Get one credit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditDTO> findOne(Long id) {
        log.debug("Request to get Credit : {}", id);
        return creditRepository.findById(id)
            .map(creditMapper::toDto);
    }

    /**
     * Delete the credit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Credit : {}", id);
        creditRepository.deleteById(id);
    }
}
