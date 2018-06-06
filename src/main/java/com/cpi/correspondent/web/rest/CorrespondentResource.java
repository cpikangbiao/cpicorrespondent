package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentDTO;
import com.cpi.correspondent.service.dto.CorrespondentCriteria;
import com.cpi.correspondent.service.CorrespondentQueryService;
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
 * REST controller for managing Correspondent.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentResource.class);

    private static final String ENTITY_NAME = "correspondent";

    private final CorrespondentService correspondentService;

    private final CorrespondentQueryService correspondentQueryService;

    public CorrespondentResource(CorrespondentService correspondentService, CorrespondentQueryService correspondentQueryService) {
        this.correspondentService = correspondentService;
        this.correspondentQueryService = correspondentQueryService;
    }

    /**
     * POST  /correspondents : Create a new correspondent.
     *
     * @param correspondentDTO the correspondentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentDTO, or with status 400 (Bad Request) if the correspondent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondents")
    @Timed
    public ResponseEntity<CorrespondentDTO> createCorrespondent(@Valid @RequestBody CorrespondentDTO correspondentDTO) throws URISyntaxException {
        log.debug("REST request to save Correspondent : {}", correspondentDTO);
        if (correspondentDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentDTO result = correspondentService.save(correspondentDTO);
        return ResponseEntity.created(new URI("/api/correspondents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondents : Updates an existing correspondent.
     *
     * @param correspondentDTO the correspondentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentDTO,
     * or with status 400 (Bad Request) if the correspondentDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondents")
    @Timed
    public ResponseEntity<CorrespondentDTO> updateCorrespondent(@Valid @RequestBody CorrespondentDTO correspondentDTO) throws URISyntaxException {
        log.debug("REST request to update Correspondent : {}", correspondentDTO);
        if (correspondentDTO.getId() == null) {
            return createCorrespondent(correspondentDTO);
        }
        CorrespondentDTO result = correspondentService.save(correspondentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondents : get all the correspondents.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondents in body
     */
    @GetMapping("/correspondents")
    @Timed
    public ResponseEntity<List<CorrespondentDTO>> getAllCorrespondents(CorrespondentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Correspondents by criteria: {}", criteria);
        Page<CorrespondentDTO> page = correspondentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondents/:id : get the "id" correspondent.
     *
     * @param id the id of the correspondentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondents/{id}")
    @Timed
    public ResponseEntity<CorrespondentDTO> getCorrespondent(@PathVariable Long id) {
        log.debug("REST request to get Correspondent : {}", id);
        CorrespondentDTO correspondentDTO = correspondentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentDTO));
    }

    /**
     * DELETE  /correspondents/:id : delete the "id" correspondent.
     *
     * @param id the id of the correspondentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondents/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondent(@PathVariable Long id) {
        log.debug("REST request to delete Correspondent : {}", id);
        correspondentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
