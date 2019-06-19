package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.CorrespondentFeeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeCriteria;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;

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
 * REST controller for managing {@link com.cpi.correspondent.domain.CorrespondentFee}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentFeeResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentCorrespondentFee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentFeeService correspondentFeeService;

    private final CorrespondentFeeQueryService correspondentFeeQueryService;

    public CorrespondentFeeResource(CorrespondentFeeService correspondentFeeService, CorrespondentFeeQueryService correspondentFeeQueryService) {
        this.correspondentFeeService = correspondentFeeService;
        this.correspondentFeeQueryService = correspondentFeeQueryService;
    }

    /**
     * {@code POST  /correspondent-fees} : Create a new correspondentFee.
     *
     * @param correspondentFeeDTO the correspondentFeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentFeeDTO, or with status {@code 400 (Bad Request)} if the correspondentFee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-fees")
    public ResponseEntity<CorrespondentFeeDTO> createCorrespondentFee(@RequestBody CorrespondentFeeDTO correspondentFeeDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentFee : {}", correspondentFeeDTO);
        if (correspondentFeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentFeeDTO result = correspondentFeeService.save(correspondentFeeDTO);
        return ResponseEntity.created(new URI("/api/correspondent-fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-fees} : Updates an existing correspondentFee.
     *
     * @param correspondentFeeDTO the correspondentFeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentFeeDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentFeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentFeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-fees")
    public ResponseEntity<CorrespondentFeeDTO> updateCorrespondentFee(@RequestBody CorrespondentFeeDTO correspondentFeeDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentFee : {}", correspondentFeeDTO);
        if (correspondentFeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentFeeDTO result = correspondentFeeService.save(correspondentFeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentFeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-fees} : get all the correspondentFees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentFees in body.
     */
    @GetMapping("/correspondent-fees")
    public ResponseEntity<List<CorrespondentFeeDTO>> getAllCorrespondentFees(CorrespondentFeeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentFees by criteria: {}", criteria);
        Page<CorrespondentFeeDTO> page = correspondentFeeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-fees/count} : count all the correspondentFees.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-fees/count")
    public ResponseEntity<Long> countCorrespondentFees(CorrespondentFeeCriteria criteria) {
        log.debug("REST request to count CorrespondentFees by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentFeeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondent-fees/:id} : get the "id" correspondentFee.
     *
     * @param id the id of the correspondentFeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentFeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-fees/{id}")
    public ResponseEntity<CorrespondentFeeDTO> getCorrespondentFee(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentFee : {}", id);
        Optional<CorrespondentFeeDTO> correspondentFeeDTO = correspondentFeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentFeeDTO);
    }

    /**
     * {@code DELETE  /correspondent-fees/:id} : delete the "id" correspondentFee.
     *
     * @param id the id of the correspondentFeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-fees/{id}")
    public ResponseEntity<Void> deleteCorrespondentFee(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentFee : {}", id);
        correspondentFeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
