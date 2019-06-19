package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.CorrespondentBillStatusLogService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogCriteria;
import com.cpi.correspondent.service.CorrespondentBillStatusLogQueryService;

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
 * REST controller for managing {@link com.cpi.correspondent.domain.CorrespondentBillStatusLog}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusLogResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentCorrespondentBillStatusLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentBillStatusLogService correspondentBillStatusLogService;

    private final CorrespondentBillStatusLogQueryService correspondentBillStatusLogQueryService;

    public CorrespondentBillStatusLogResource(CorrespondentBillStatusLogService correspondentBillStatusLogService, CorrespondentBillStatusLogQueryService correspondentBillStatusLogQueryService) {
        this.correspondentBillStatusLogService = correspondentBillStatusLogService;
        this.correspondentBillStatusLogQueryService = correspondentBillStatusLogQueryService;
    }

    /**
     * {@code POST  /correspondent-bill-status-logs} : Create a new correspondentBillStatusLog.
     *
     * @param correspondentBillStatusLogDTO the correspondentBillStatusLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentBillStatusLogDTO, or with status {@code 400 (Bad Request)} if the correspondentBillStatusLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-bill-status-logs")
    public ResponseEntity<CorrespondentBillStatusLogDTO> createCorrespondentBillStatusLog(@Valid @RequestBody CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentBillStatusLog : {}", correspondentBillStatusLogDTO);
        if (correspondentBillStatusLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentBillStatusLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentBillStatusLogDTO result = correspondentBillStatusLogService.save(correspondentBillStatusLogDTO);
        return ResponseEntity.created(new URI("/api/correspondent-bill-status-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-bill-status-logs} : Updates an existing correspondentBillStatusLog.
     *
     * @param correspondentBillStatusLogDTO the correspondentBillStatusLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentBillStatusLogDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentBillStatusLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentBillStatusLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-bill-status-logs")
    public ResponseEntity<CorrespondentBillStatusLogDTO> updateCorrespondentBillStatusLog(@Valid @RequestBody CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentBillStatusLog : {}", correspondentBillStatusLogDTO);
        if (correspondentBillStatusLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentBillStatusLogDTO result = correspondentBillStatusLogService.save(correspondentBillStatusLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentBillStatusLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-bill-status-logs} : get all the correspondentBillStatusLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentBillStatusLogs in body.
     */
    @GetMapping("/correspondent-bill-status-logs")
    public ResponseEntity<List<CorrespondentBillStatusLogDTO>> getAllCorrespondentBillStatusLogs(CorrespondentBillStatusLogCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentBillStatusLogs by criteria: {}", criteria);
        Page<CorrespondentBillStatusLogDTO> page = correspondentBillStatusLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-bill-status-logs/count} : count all the correspondentBillStatusLogs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-bill-status-logs/count")
    public ResponseEntity<Long> countCorrespondentBillStatusLogs(CorrespondentBillStatusLogCriteria criteria) {
        log.debug("REST request to count CorrespondentBillStatusLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentBillStatusLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondent-bill-status-logs/:id} : get the "id" correspondentBillStatusLog.
     *
     * @param id the id of the correspondentBillStatusLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentBillStatusLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-bill-status-logs/{id}")
    public ResponseEntity<CorrespondentBillStatusLogDTO> getCorrespondentBillStatusLog(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentBillStatusLog : {}", id);
        Optional<CorrespondentBillStatusLogDTO> correspondentBillStatusLogDTO = correspondentBillStatusLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentBillStatusLogDTO);
    }

    /**
     * {@code DELETE  /correspondent-bill-status-logs/:id} : delete the "id" correspondentBillStatusLog.
     *
     * @param id the id of the correspondentBillStatusLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-bill-status-logs/{id}")
    public ResponseEntity<Void> deleteCorrespondentBillStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentBillStatusLog : {}", id);
        correspondentBillStatusLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
