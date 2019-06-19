package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.CorrespondentDocsService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import com.cpi.correspondent.service.dto.CorrespondentDocsCriteria;
import com.cpi.correspondent.service.CorrespondentDocsQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.correspondent.domain.CorrespondentDocs}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentDocsResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentDocsResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentCorrespondentDocs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentDocsService correspondentDocsService;

    private final CorrespondentDocsQueryService correspondentDocsQueryService;

    public CorrespondentDocsResource(CorrespondentDocsService correspondentDocsService, CorrespondentDocsQueryService correspondentDocsQueryService) {
        this.correspondentDocsService = correspondentDocsService;
        this.correspondentDocsQueryService = correspondentDocsQueryService;
    }

    /**
     * {@code POST  /correspondent-docs} : Create a new correspondentDocs.
     *
     * @param correspondentDocsDTO the correspondentDocsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentDocsDTO, or with status {@code 400 (Bad Request)} if the correspondentDocs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-docs")
    public ResponseEntity<CorrespondentDocsDTO> createCorrespondentDocs(@RequestBody CorrespondentDocsDTO correspondentDocsDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentDocs : {}", correspondentDocsDTO);
        if (correspondentDocsDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentDocs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentDocsDTO result = correspondentDocsService.save(correspondentDocsDTO);
        return ResponseEntity.created(new URI("/api/correspondent-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-docs} : Updates an existing correspondentDocs.
     *
     * @param correspondentDocsDTO the correspondentDocsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentDocsDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentDocsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentDocsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-docs")
    public ResponseEntity<CorrespondentDocsDTO> updateCorrespondentDocs(@RequestBody CorrespondentDocsDTO correspondentDocsDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentDocs : {}", correspondentDocsDTO);
        if (correspondentDocsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentDocsDTO result = correspondentDocsService.save(correspondentDocsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentDocsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-docs} : get all the correspondentDocs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentDocs in body.
     */
    @GetMapping("/correspondent-docs")
    public ResponseEntity<List<CorrespondentDocsDTO>> getAllCorrespondentDocs(CorrespondentDocsCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentDocs by criteria: {}", criteria);
        Page<CorrespondentDocsDTO> page = correspondentDocsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-docs/count} : count all the correspondentDocs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-docs/count")
    public ResponseEntity<Long> countCorrespondentDocs(CorrespondentDocsCriteria criteria) {
        log.debug("REST request to count CorrespondentDocs by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentDocsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondent-docs/:id} : get the "id" correspondentDocs.
     *
     * @param id the id of the correspondentDocsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentDocsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-docs/{id}")
    public ResponseEntity<CorrespondentDocsDTO> getCorrespondentDocs(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentDocs : {}", id);
        Optional<CorrespondentDocsDTO> correspondentDocsDTO = correspondentDocsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentDocsDTO);
    }

    /**
     * {@code DELETE  /correspondent-docs/:id} : delete the "id" correspondentDocs.
     *
     * @param id the id of the correspondentDocsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-docs/{id}")
    public ResponseEntity<Void> deleteCorrespondentDocs(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentDocs : {}", id);
        correspondentDocsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
