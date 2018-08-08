package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentBillStatusLogService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogCriteria;
import com.cpi.correspondent.service.CorrespondentBillStatusLogQueryService;
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
 * REST controller for managing CorrespondentBillStatusLog.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusLogResource.class);

    private static final String ENTITY_NAME = "correspondentBillStatusLog";

    private final CorrespondentBillStatusLogService correspondentBillStatusLogService;

    private final CorrespondentBillStatusLogQueryService correspondentBillStatusLogQueryService;

    public CorrespondentBillStatusLogResource(CorrespondentBillStatusLogService correspondentBillStatusLogService, CorrespondentBillStatusLogQueryService correspondentBillStatusLogQueryService) {
        this.correspondentBillStatusLogService = correspondentBillStatusLogService;
        this.correspondentBillStatusLogQueryService = correspondentBillStatusLogQueryService;
    }

    /**
     * POST  /correspondent-bill-status-logs : Create a new correspondentBillStatusLog.
     *
     * @param correspondentBillStatusLogDTO the correspondentBillStatusLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentBillStatusLogDTO, or with status 400 (Bad Request) if the correspondentBillStatusLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-bill-status-logs")
    @Timed
    public ResponseEntity<CorrespondentBillStatusLogDTO> createCorrespondentBillStatusLog(@Valid @RequestBody CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentBillStatusLog : {}", correspondentBillStatusLogDTO);
        if (correspondentBillStatusLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentBillStatusLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentBillStatusLogDTO result = correspondentBillStatusLogService.save(correspondentBillStatusLogDTO);
        return ResponseEntity.created(new URI("/api/correspondent-bill-status-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-bill-status-logs : Updates an existing correspondentBillStatusLog.
     *
     * @param correspondentBillStatusLogDTO the correspondentBillStatusLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentBillStatusLogDTO,
     * or with status 400 (Bad Request) if the correspondentBillStatusLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentBillStatusLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-bill-status-logs")
    @Timed
    public ResponseEntity<CorrespondentBillStatusLogDTO> updateCorrespondentBillStatusLog(@Valid @RequestBody CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentBillStatusLog : {}", correspondentBillStatusLogDTO);
        if (correspondentBillStatusLogDTO.getId() == null) {
            return createCorrespondentBillStatusLog(correspondentBillStatusLogDTO);
        }
        CorrespondentBillStatusLogDTO result = correspondentBillStatusLogService.save(correspondentBillStatusLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentBillStatusLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-bill-status-logs : get all the correspondentBillStatusLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentBillStatusLogs in body
     */
    @GetMapping("/correspondent-bill-status-logs")
    @Timed
    public ResponseEntity<List<CorrespondentBillStatusLogDTO>> getAllCorrespondentBillStatusLogs(CorrespondentBillStatusLogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentBillStatusLogs by criteria: {}", criteria);
        Page<CorrespondentBillStatusLogDTO> page = correspondentBillStatusLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-bill-status-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/correspondent-bill-status-logs/by-bill/{billId}")
    @Timed
    public ResponseEntity<List<CorrespondentBillStatusLogDTO>> getAllCorrespondentBillStatusLogs(@PathVariable Long billId) {
        log.debug("REST request to get CorrespondentBillStatusLogs by billId: {}", billId);
        List<CorrespondentBillStatusLogDTO> page = correspondentBillStatusLogQueryService.findByCorrespondentBillId(billId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * GET  /correspondent-bill-status-logs/:id : get the "id" correspondentBillStatusLog.
     *
     * @param id the id of the correspondentBillStatusLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentBillStatusLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-bill-status-logs/{id}")
    @Timed
    public ResponseEntity<CorrespondentBillStatusLogDTO> getCorrespondentBillStatusLog(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentBillStatusLog : {}", id);
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentBillStatusLogDTO));
    }

    /**
     * DELETE  /correspondent-bill-status-logs/:id : delete the "id" correspondentBillStatusLog.
     *
     * @param id the id of the correspondentBillStatusLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-bill-status-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentBillStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentBillStatusLog : {}", id);
        correspondentBillStatusLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}