package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CreditService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CreditDTO;
import com.cpi.correspondent.service.dto.CreditCriteria;
import com.cpi.correspondent.service.CreditQueryService;
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
 * REST controller for managing Credit.
 */
@RestController
@RequestMapping("/api")
public class CreditResource {

    private final Logger log = LoggerFactory.getLogger(CreditResource.class);

    private static final String ENTITY_NAME = "credit";

    private final CreditService creditService;

    private final CreditQueryService creditQueryService;

    public CreditResource(CreditService creditService, CreditQueryService creditQueryService) {
        this.creditService = creditService;
        this.creditQueryService = creditQueryService;
    }

    /**
     * POST  /credits : Create a new credit.
     *
     * @param creditDTO the creditDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditDTO, or with status 400 (Bad Request) if the credit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credits")
    @Timed
    public ResponseEntity<CreditDTO> createCredit(@RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to save Credit : {}", creditDTO);
        if (creditDTO.getId() != null) {
            throw new BadRequestAlertException("A new credit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.created(new URI("/api/credits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credits : Updates an existing credit.
     *
     * @param creditDTO the creditDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditDTO,
     * or with status 400 (Bad Request) if the creditDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credits")
    @Timed
    public ResponseEntity<CreditDTO> updateCredit(@RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to update Credit : {}", creditDTO);
        if (creditDTO.getId() == null) {
            return createCredit(creditDTO);
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credits : get all the credits.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of credits in body
     */
    @GetMapping("/credits")
    @Timed
    public ResponseEntity<List<CreditDTO>> getAllCredits(CreditCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Credits by criteria: {}", criteria);
        Page<CreditDTO> page = creditQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/credits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /credits/:id : get the "id" credit.
     *
     * @param id the id of the creditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credits/{id}")
    @Timed
    public ResponseEntity<CreditDTO> getCredit(@PathVariable Long id) {
        log.debug("REST request to get Credit : {}", id);
        CreditDTO creditDTO = creditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(creditDTO));
    }

    /**
     * DELETE  /credits/:id : delete the "id" credit.
     *
     * @param id the id of the creditDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credits/{id}")
    @Timed
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        log.debug("REST request to delete Credit : {}", id);
        creditService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
