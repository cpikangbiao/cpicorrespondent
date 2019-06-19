package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.CreditService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CreditDTO;
import com.cpi.correspondent.service.dto.CreditCriteria;
import com.cpi.correspondent.service.CreditQueryService;

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
 * REST controller for managing {@link com.cpi.correspondent.domain.Credit}.
 */
@RestController
@RequestMapping("/api")
public class CreditResource {

    private final Logger log = LoggerFactory.getLogger(CreditResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentCredit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditService creditService;

    private final CreditQueryService creditQueryService;

    public CreditResource(CreditService creditService, CreditQueryService creditQueryService) {
        this.creditService = creditService;
        this.creditQueryService = creditQueryService;
    }

    /**
     * {@code POST  /credits} : Create a new credit.
     *
     * @param creditDTO the creditDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditDTO, or with status {@code 400 (Bad Request)} if the credit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credits")
    public ResponseEntity<CreditDTO> createCredit(@RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to save Credit : {}", creditDTO);
        if (creditDTO.getId() != null) {
            throw new BadRequestAlertException("A new credit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.created(new URI("/api/credits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credits} : Updates an existing credit.
     *
     * @param creditDTO the creditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditDTO,
     * or with status {@code 400 (Bad Request)} if the creditDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credits")
    public ResponseEntity<CreditDTO> updateCredit(@RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to update Credit : {}", creditDTO);
        if (creditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /credits} : get all the credits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of credits in body.
     */
    @GetMapping("/credits")
    public ResponseEntity<List<CreditDTO>> getAllCredits(CreditCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Credits by criteria: {}", criteria);
        Page<CreditDTO> page = creditQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /credits/count} : count all the credits.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/credits/count")
    public ResponseEntity<Long> countCredits(CreditCriteria criteria) {
        log.debug("REST request to count Credits by criteria: {}", criteria);
        return ResponseEntity.ok().body(creditQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /credits/:id} : get the "id" credit.
     *
     * @param id the id of the creditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credits/{id}")
    public ResponseEntity<CreditDTO> getCredit(@PathVariable Long id) {
        log.debug("REST request to get Credit : {}", id);
        Optional<CreditDTO> creditDTO = creditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditDTO);
    }

    /**
     * {@code DELETE  /credits/:id} : delete the "id" credit.
     *
     * @param id the id of the creditDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credits/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        log.debug("REST request to delete Credit : {}", id);
        creditService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
