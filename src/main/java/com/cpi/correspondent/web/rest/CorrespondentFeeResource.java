package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentFeeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeCriteria;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorrespondentFee.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentFeeResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeResource.class);

    private static final String ENTITY_NAME = "correspondentFee";

    private final CorrespondentFeeService correspondentFeeService;

    private final CorrespondentFeeQueryService correspondentFeeQueryService;

    public CorrespondentFeeResource(CorrespondentFeeService correspondentFeeService, CorrespondentFeeQueryService correspondentFeeQueryService) {
        this.correspondentFeeService = correspondentFeeService;
        this.correspondentFeeQueryService = correspondentFeeQueryService;
    }

    /**
     * POST  /correspondent-fees : Create a new correspondentFee.
     *
     * @param correspondentFeeDTO the correspondentFeeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentFeeDTO, or with status 400 (Bad Request) if the correspondentFee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-fees")
    @Timed
    public ResponseEntity<CorrespondentFeeDTO> createCorrespondentFee(@RequestBody CorrespondentFeeDTO correspondentFeeDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentFee : {}", correspondentFeeDTO);
        if (correspondentFeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentFeeDTO result = correspondentFeeService.save(correspondentFeeDTO);
        return ResponseEntity.created(new URI("/api/correspondent-fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-fees : Updates an existing correspondentFee.
     *
     * @param correspondentFeeDTO the correspondentFeeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentFeeDTO,
     * or with status 400 (Bad Request) if the correspondentFeeDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentFeeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-fees")
    @Timed
    public ResponseEntity<CorrespondentFeeDTO> updateCorrespondentFee(@RequestBody CorrespondentFeeDTO correspondentFeeDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentFee : {}", correspondentFeeDTO);
        if (correspondentFeeDTO.getId() == null) {
            return createCorrespondentFee(correspondentFeeDTO);
        }
        CorrespondentFeeDTO result = correspondentFeeService.save(correspondentFeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentFeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-fees : get all the correspondentFees.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentFees in body
     */
    @GetMapping("/correspondent-fees")
    @Timed
    public ResponseEntity<List<CorrespondentFeeDTO>> getAllCorrespondentFees(CorrespondentFeeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentFees by criteria: {}", criteria);
        Page<CorrespondentFeeDTO> page = correspondentFeeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-fees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-fees/:id : get the "id" correspondentFee.
     *
     * @param id the id of the correspondentFeeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentFeeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-fees/{id}")
    @Timed
    public ResponseEntity<CorrespondentFeeDTO> getCorrespondentFee(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentFee : {}", id);
        CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentFeeDTO));
    }

    /**
     * DELETE  /correspondent-fees/:id : delete the "id" correspondentFee.
     *
     * @param id the id of the correspondentFeeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-fees/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentFee(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentFee : {}", id);
        correspondentFeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
