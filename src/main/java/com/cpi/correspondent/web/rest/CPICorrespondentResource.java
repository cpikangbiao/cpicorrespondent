package com.cpi.correspondent.web.rest;


import com.cpi.correspondent.service.*;

import com.cpi.correspondent.service.CPICorrespondentService;

import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;

import com.cpi.correspondent.service.CPICorrespondentQueryService;

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

import java.util.*;

/**
 * REST controller for managing {@link com.cpi.correspondent.domain.CPICorrespondent}.
 */
@RestController
@RequestMapping("/api")
public class CPICorrespondentResource {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentResource.class);

    private final static  Long EXCEL_TEMPLATE_FOR_1 = new Long(1);

    private static final String ENTITY_NAME = "cpicorrespondentCpiCorrespondent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPICorrespondentService cPICorrespondentService;

    private final CPICorrespondentQueryService cPICorrespondentQueryService;

    public CPICorrespondentResource(CPICorrespondentService cPICorrespondentService, CPICorrespondentQueryService cPICorrespondentQueryService) {
        this.cPICorrespondentService = cPICorrespondentService;
        this.cPICorrespondentQueryService = cPICorrespondentQueryService;
    }

    /**
     * {@code POST  /cpi-correspondents} : Create a new cPICorrespondent.
     *
     * @param cPICorrespondentDTO the cPICorrespondentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPICorrespondentDTO, or with status {@code 400 (Bad Request)} if the cPICorrespondent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cpi-correspondents")
    public ResponseEntity<CPICorrespondentDTO> createCPICorrespondent(@Valid @RequestBody CPICorrespondentDTO cPICorrespondentDTO) throws URISyntaxException {
        log.debug("REST request to save CPICorrespondent : {}", cPICorrespondentDTO);
        if (cPICorrespondentDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPICorrespondent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPICorrespondentDTO result = cPICorrespondentService.save(cPICorrespondentDTO);
        return ResponseEntity.created(new URI("/api/cpi-correspondents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cpi-correspondents} : Updates an existing cPICorrespondent.
     *
     * @param cPICorrespondentDTO the cPICorrespondentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPICorrespondentDTO,
     * or with status {@code 400 (Bad Request)} if the cPICorrespondentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPICorrespondentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cpi-correspondents")
    public ResponseEntity<CPICorrespondentDTO> updateCPICorrespondent(@Valid @RequestBody CPICorrespondentDTO cPICorrespondentDTO) throws URISyntaxException {
        log.debug("REST request to update CPICorrespondent : {}", cPICorrespondentDTO);
        if (cPICorrespondentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPICorrespondentDTO result = cPICorrespondentService.save(cPICorrespondentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPICorrespondentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cpi-correspondents} : get all the cPICorrespondents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPICorrespondents in body.
     */
    @GetMapping("/cpi-correspondents")
    public ResponseEntity<List<CPICorrespondentDTO>> getAllCPICorrespondents(CPICorrespondentCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CPICorrespondents by criteria: {}", criteria);
        Page<CPICorrespondentDTO> page = cPICorrespondentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /cpi-correspondents/count} : count all the cPICorrespondents.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cpi-correspondents/count")
    public ResponseEntity<Long> countCPICorrespondents(CPICorrespondentCriteria criteria) {
        log.debug("REST request to count CPICorrespondents by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPICorrespondentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cpi-correspondents/:id} : get the "id" cPICorrespondent.
     *
     * @param id the id of the cPICorrespondentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPICorrespondentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cpi-correspondents/{id}")
    public ResponseEntity<CPICorrespondentDTO> getCPICorrespondent(@PathVariable Long id) {
        log.debug("REST request to get CPICorrespondent : {}", id);
        Optional<CPICorrespondentDTO> cPICorrespondentDTO = cPICorrespondentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPICorrespondentDTO);
    }

    /**
     * {@code DELETE  /cpi-correspondents/:id} : delete the "id" cPICorrespondent.
     *
     * @param id the id of the cPICorrespondentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cpi-correspondents/{id}")
    public ResponseEntity<Void> deleteCPICorrespondent(@PathVariable Long id) {
        log.debug("REST request to delete CPICorrespondent : {}", id);
        cPICorrespondentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
