package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentBillStatusService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusCriteria;
import com.cpi.correspondent.service.CorrespondentBillStatusQueryService;
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
 * REST controller for managing CorrespondentBillStatus.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillStatusResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusResource.class);

    private static final String ENTITY_NAME = "correspondentBillStatus";

    private final CorrespondentBillStatusService correspondentBillStatusService;

    private final CorrespondentBillStatusQueryService correspondentBillStatusQueryService;

    public CorrespondentBillStatusResource(CorrespondentBillStatusService correspondentBillStatusService, CorrespondentBillStatusQueryService correspondentBillStatusQueryService) {
        this.correspondentBillStatusService = correspondentBillStatusService;
        this.correspondentBillStatusQueryService = correspondentBillStatusQueryService;
    }

    /**
     * POST  /correspondent-bill-statuses : Create a new correspondentBillStatus.
     *
     * @param correspondentBillStatusDTO the correspondentBillStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentBillStatusDTO, or with status 400 (Bad Request) if the correspondentBillStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-bill-statuses")
    @Timed
    public ResponseEntity<CorrespondentBillStatusDTO> createCorrespondentBillStatus(@Valid @RequestBody CorrespondentBillStatusDTO correspondentBillStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentBillStatus : {}", correspondentBillStatusDTO);
        if (correspondentBillStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentBillStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentBillStatusDTO result = correspondentBillStatusService.save(correspondentBillStatusDTO);
        return ResponseEntity.created(new URI("/api/correspondent-bill-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-bill-statuses : Updates an existing correspondentBillStatus.
     *
     * @param correspondentBillStatusDTO the correspondentBillStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentBillStatusDTO,
     * or with status 400 (Bad Request) if the correspondentBillStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentBillStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-bill-statuses")
    @Timed
    public ResponseEntity<CorrespondentBillStatusDTO> updateCorrespondentBillStatus(@Valid @RequestBody CorrespondentBillStatusDTO correspondentBillStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentBillStatus : {}", correspondentBillStatusDTO);
        if (correspondentBillStatusDTO.getId() == null) {
            return createCorrespondentBillStatus(correspondentBillStatusDTO);
        }
        CorrespondentBillStatusDTO result = correspondentBillStatusService.save(correspondentBillStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentBillStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-bill-statuses : get all the correspondentBillStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentBillStatuses in body
     */
    @GetMapping("/correspondent-bill-statuses")
    @Timed
    public ResponseEntity<List<CorrespondentBillStatusDTO>> getAllCorrespondentBillStatuses(CorrespondentBillStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentBillStatuses by criteria: {}", criteria);
        Page<CorrespondentBillStatusDTO> page = correspondentBillStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-bill-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-bill-statuses/:id : get the "id" correspondentBillStatus.
     *
     * @param id the id of the correspondentBillStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentBillStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-bill-statuses/{id}")
    @Timed
    public ResponseEntity<CorrespondentBillStatusDTO> getCorrespondentBillStatus(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentBillStatus : {}", id);
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentBillStatusDTO));
    }

    /**
     * DELETE  /correspondent-bill-statuses/:id : delete the "id" correspondentBillStatus.
     *
     * @param id the id of the correspondentBillStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-bill-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentBillStatus(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentBillStatus : {}", id);
        correspondentBillStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
