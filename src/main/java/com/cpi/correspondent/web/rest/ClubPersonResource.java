package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.ClubPersonService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.ClubPersonDTO;
import com.cpi.correspondent.service.dto.ClubPersonCriteria;
import com.cpi.correspondent.service.ClubPersonQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.correspondent.domain.ClubPerson}.
 */
@RestController
@RequestMapping("/api")
public class ClubPersonResource {

    private final Logger log = LoggerFactory.getLogger(ClubPersonResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentClubPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClubPersonService clubPersonService;

    private final ClubPersonQueryService clubPersonQueryService;

    public ClubPersonResource(ClubPersonService clubPersonService, ClubPersonQueryService clubPersonQueryService) {
        this.clubPersonService = clubPersonService;
        this.clubPersonQueryService = clubPersonQueryService;
    }

    /**
     * {@code POST  /club-people} : Create a new clubPerson.
     *
     * @param clubPersonDTO the clubPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clubPersonDTO, or with status {@code 400 (Bad Request)} if the clubPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/club-people")
    public ResponseEntity<ClubPersonDTO> createClubPerson(@Valid @RequestBody ClubPersonDTO clubPersonDTO) throws URISyntaxException {
        log.debug("REST request to save ClubPerson : {}", clubPersonDTO);
        if (clubPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new clubPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClubPersonDTO result = clubPersonService.save(clubPersonDTO);
        return ResponseEntity.created(new URI("/api/club-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /club-people} : Updates an existing clubPerson.
     *
     * @param clubPersonDTO the clubPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clubPersonDTO,
     * or with status {@code 400 (Bad Request)} if the clubPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clubPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/club-people")
    public ResponseEntity<ClubPersonDTO> updateClubPerson(@Valid @RequestBody ClubPersonDTO clubPersonDTO) throws URISyntaxException {
        log.debug("REST request to update ClubPerson : {}", clubPersonDTO);
        if (clubPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClubPersonDTO result = clubPersonService.save(clubPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clubPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /club-people} : get all the clubPeople.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clubPeople in body.
     */
    @GetMapping("/club-people")
    public ResponseEntity<List<ClubPersonDTO>> getAllClubPeople(ClubPersonCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ClubPeople by criteria: {}", criteria);
        Page<ClubPersonDTO> page = clubPersonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /club-people/count} : count all the clubPeople.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/club-people/count")
    public ResponseEntity<Long> countClubPeople(ClubPersonCriteria criteria) {
        log.debug("REST request to count ClubPeople by criteria: {}", criteria);
        return ResponseEntity.ok().body(clubPersonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /club-people/:id} : get the "id" clubPerson.
     *
     * @param id the id of the clubPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clubPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/club-people/{id}")
    public ResponseEntity<ClubPersonDTO> getClubPerson(@PathVariable Long id) {
        log.debug("REST request to get ClubPerson : {}", id);
        Optional<ClubPersonDTO> clubPersonDTO = clubPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clubPersonDTO);
    }

    /**
     * {@code DELETE  /club-people/:id} : delete the "id" clubPerson.
     *
     * @param id the id of the clubPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/club-people/{id}")
    public ResponseEntity<Void> deleteClubPerson(@PathVariable Long id) {
        log.debug("REST request to delete ClubPerson : {}", id);
        clubPersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
