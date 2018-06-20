package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.ClubPersonService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.ClubPersonDTO;
import com.cpi.correspondent.service.dto.ClubPersonCriteria;
import com.cpi.correspondent.service.ClubPersonQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClubPerson.
 */
@RestController
@RequestMapping("/api")
public class ClubPersonResource {

    private final Logger log = LoggerFactory.getLogger(ClubPersonResource.class);

    private static final String ENTITY_NAME = "clubPerson";

    private final ClubPersonService clubPersonService;

    private final ClubPersonQueryService clubPersonQueryService;

    public ClubPersonResource(ClubPersonService clubPersonService, ClubPersonQueryService clubPersonQueryService) {
        this.clubPersonService = clubPersonService;
        this.clubPersonQueryService = clubPersonQueryService;
    }

    /**
     * POST  /club-people : Create a new clubPerson.
     *
     * @param clubPersonDTO the clubPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clubPersonDTO, or with status 400 (Bad Request) if the clubPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/club-people")
    @Timed
    public ResponseEntity<ClubPersonDTO> createClubPerson(@Valid @RequestBody ClubPersonDTO clubPersonDTO) throws URISyntaxException {
        log.debug("REST request to save ClubPerson : {}", clubPersonDTO);
        if (clubPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new clubPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClubPersonDTO result = clubPersonService.save(clubPersonDTO);
        return ResponseEntity.created(new URI("/api/club-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /club-people : Updates an existing clubPerson.
     *
     * @param clubPersonDTO the clubPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clubPersonDTO,
     * or with status 400 (Bad Request) if the clubPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the clubPersonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/club-people")
    @Timed
    public ResponseEntity<ClubPersonDTO> updateClubPerson(@Valid @RequestBody ClubPersonDTO clubPersonDTO) throws URISyntaxException {
        log.debug("REST request to update ClubPerson : {}", clubPersonDTO);
        if (clubPersonDTO.getId() == null) {
            return createClubPerson(clubPersonDTO);
        }
        ClubPersonDTO result = clubPersonService.save(clubPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clubPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /club-people : get all the clubPeople.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clubPeople in body
     */
    @GetMapping("/club-people")
    @Timed
    public ResponseEntity<List<ClubPersonDTO>> getAllClubPeople(ClubPersonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClubPeople by criteria: {}", criteria);
        Page<ClubPersonDTO> page = clubPersonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/club-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /club-people/:id : get the "id" clubPerson.
     *
     * @param id the id of the clubPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clubPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/club-people/{id}")
    @Timed
    public ResponseEntity<ClubPersonDTO> getClubPerson(@PathVariable Long id) {
        log.debug("REST request to get ClubPerson : {}", id);
        ClubPersonDTO clubPersonDTO = clubPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clubPersonDTO));
    }

    /**
     * DELETE  /club-people/:id : delete the "id" clubPerson.
     *
     * @param id the id of the clubPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/club-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteClubPerson(@PathVariable Long id) {
        log.debug("REST request to delete ClubPerson : {}", id);
        clubPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
