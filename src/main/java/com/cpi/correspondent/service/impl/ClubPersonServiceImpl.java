package com.cpi.correspondent.service.impl;

import com.cpi.correspondent.service.ClubPersonService;
import com.cpi.correspondent.domain.ClubPerson;
import com.cpi.correspondent.repository.ClubPersonRepository;
import com.cpi.correspondent.service.dto.ClubPersonDTO;
import com.cpi.correspondent.service.mapper.ClubPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClubPerson}.
 */
@Service
@Transactional
public class ClubPersonServiceImpl implements ClubPersonService {

    private final Logger log = LoggerFactory.getLogger(ClubPersonServiceImpl.class);

    private final ClubPersonRepository clubPersonRepository;

    private final ClubPersonMapper clubPersonMapper;

    public ClubPersonServiceImpl(ClubPersonRepository clubPersonRepository, ClubPersonMapper clubPersonMapper) {
        this.clubPersonRepository = clubPersonRepository;
        this.clubPersonMapper = clubPersonMapper;
    }

    /**
     * Save a clubPerson.
     *
     * @param clubPersonDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClubPersonDTO save(ClubPersonDTO clubPersonDTO) {
        log.debug("Request to save ClubPerson : {}", clubPersonDTO);
        ClubPerson clubPerson = clubPersonMapper.toEntity(clubPersonDTO);
        clubPerson = clubPersonRepository.save(clubPerson);
        return clubPersonMapper.toDto(clubPerson);
    }

    /**
     * Get all the clubPeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClubPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClubPeople");
        return clubPersonRepository.findAll(pageable)
            .map(clubPersonMapper::toDto);
    }


    /**
     * Get one clubPerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClubPersonDTO> findOne(Long id) {
        log.debug("Request to get ClubPerson : {}", id);
        return clubPersonRepository.findById(id)
            .map(clubPersonMapper::toDto);
    }

    /**
     * Delete the clubPerson by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClubPerson : {}", id);
        clubPersonRepository.deleteById(id);
    }
}
