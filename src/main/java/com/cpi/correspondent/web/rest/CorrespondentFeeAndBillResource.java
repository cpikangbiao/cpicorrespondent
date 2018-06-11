package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentFeeAndBillService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillCriteria;
import com.cpi.correspondent.service.CorrespondentFeeAndBillQueryService;
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
 * REST controller for managing CorrespondentFeeAndBill.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentFeeAndBillResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeAndBillResource.class);

    private static final String ENTITY_NAME = "correspondentFeeAndBill";

    private final CorrespondentFeeAndBillService correspondentFeeAndBillService;

    private final CorrespondentFeeAndBillQueryService correspondentFeeAndBillQueryService;

    public CorrespondentFeeAndBillResource(CorrespondentFeeAndBillService correspondentFeeAndBillService, CorrespondentFeeAndBillQueryService correspondentFeeAndBillQueryService) {
        this.correspondentFeeAndBillService = correspondentFeeAndBillService;
        this.correspondentFeeAndBillQueryService = correspondentFeeAndBillQueryService;
    }

    /**
     * POST  /correspondent-fee-and-bills : Create a new correspondentFeeAndBill.
     *
     * @param correspondentFeeAndBillDTO the correspondentFeeAndBillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentFeeAndBillDTO, or with status 400 (Bad Request) if the correspondentFeeAndBill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-fee-and-bills")
    @Timed
    public ResponseEntity<CorrespondentFeeAndBillDTO> createCorrespondentFeeAndBill(@RequestBody CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentFeeAndBill : {}", correspondentFeeAndBillDTO);
        if (correspondentFeeAndBillDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentFeeAndBill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentFeeAndBillDTO result = correspondentFeeAndBillService.save(correspondentFeeAndBillDTO);
        return ResponseEntity.created(new URI("/api/correspondent-fee-and-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-fee-and-bills : Updates an existing correspondentFeeAndBill.
     *
     * @param correspondentFeeAndBillDTO the correspondentFeeAndBillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentFeeAndBillDTO,
     * or with status 400 (Bad Request) if the correspondentFeeAndBillDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentFeeAndBillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-fee-and-bills")
    @Timed
    public ResponseEntity<CorrespondentFeeAndBillDTO> updateCorrespondentFeeAndBill(@RequestBody CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentFeeAndBill : {}", correspondentFeeAndBillDTO);
        if (correspondentFeeAndBillDTO.getId() == null) {
            return createCorrespondentFeeAndBill(correspondentFeeAndBillDTO);
        }
        CorrespondentFeeAndBillDTO result = correspondentFeeAndBillService.save(correspondentFeeAndBillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentFeeAndBillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-fee-and-bills : get all the correspondentFeeAndBills.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentFeeAndBills in body
     */
    @GetMapping("/correspondent-fee-and-bills")
    @Timed
    public ResponseEntity<List<CorrespondentFeeAndBillDTO>> getAllCorrespondentFeeAndBills(CorrespondentFeeAndBillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentFeeAndBills by criteria: {}", criteria);
        Page<CorrespondentFeeAndBillDTO> page = correspondentFeeAndBillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-fee-and-bills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-fee-and-bills/:id : get the "id" correspondentFeeAndBill.
     *
     * @param id the id of the correspondentFeeAndBillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentFeeAndBillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-fee-and-bills/{id}")
    @Timed
    public ResponseEntity<CorrespondentFeeAndBillDTO> getCorrespondentFeeAndBill(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentFeeAndBill : {}", id);
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO = correspondentFeeAndBillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentFeeAndBillDTO));
    }

    /**
     * DELETE  /correspondent-fee-and-bills/:id : delete the "id" correspondentFeeAndBill.
     *
     * @param id the id of the correspondentFeeAndBillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-fee-and-bills/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentFeeAndBill(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentFeeAndBill : {}", id);
        correspondentFeeAndBillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
