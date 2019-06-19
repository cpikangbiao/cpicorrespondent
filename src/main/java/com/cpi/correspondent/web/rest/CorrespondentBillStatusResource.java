package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.CorrespondentBillStatusService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusCriteria;
import com.cpi.correspondent.service.CorrespondentBillStatusQueryService;

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
 * REST controller for managing {@link com.cpi.correspondent.domain.CorrespondentBillStatus}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillStatusResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentCorrespondentBillStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentBillStatusService correspondentBillStatusService;

    private final CorrespondentBillStatusQueryService correspondentBillStatusQueryService;

    public CorrespondentBillStatusResource(CorrespondentBillStatusService correspondentBillStatusService, CorrespondentBillStatusQueryService correspondentBillStatusQueryService) {
        this.correspondentBillStatusService = correspondentBillStatusService;
        this.correspondentBillStatusQueryService = correspondentBillStatusQueryService;
    }

    /**
     * {@code POST  /correspondent-bill-statuses} : Create a new correspondentBillStatus.
     *
     * @param correspondentBillStatusDTO the correspondentBillStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentBillStatusDTO, or with status {@code 400 (Bad Request)} if the correspondentBillStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-bill-statuses")
    public ResponseEntity<CorrespondentBillStatusDTO> createCorrespondentBillStatus(@Valid @RequestBody CorrespondentBillStatusDTO correspondentBillStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentBillStatus : {}", correspondentBillStatusDTO);
        if (correspondentBillStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentBillStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentBillStatusDTO result = correspondentBillStatusService.save(correspondentBillStatusDTO);
        return ResponseEntity.created(new URI("/api/correspondent-bill-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-bill-statuses} : Updates an existing correspondentBillStatus.
     *
     * @param correspondentBillStatusDTO the correspondentBillStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentBillStatusDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentBillStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentBillStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-bill-statuses")
    public ResponseEntity<CorrespondentBillStatusDTO> updateCorrespondentBillStatus(@Valid @RequestBody CorrespondentBillStatusDTO correspondentBillStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentBillStatus : {}", correspondentBillStatusDTO);
        if (correspondentBillStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentBillStatusDTO result = correspondentBillStatusService.save(correspondentBillStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentBillStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-bill-statuses} : get all the correspondentBillStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentBillStatuses in body.
     */
    @GetMapping("/correspondent-bill-statuses")
    public ResponseEntity<List<CorrespondentBillStatusDTO>> getAllCorrespondentBillStatuses(CorrespondentBillStatusCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentBillStatuses by criteria: {}", criteria);
        Page<CorrespondentBillStatusDTO> page = correspondentBillStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-bill-statuses/count} : count all the correspondentBillStatuses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-bill-statuses/count")
    public ResponseEntity<Long> countCorrespondentBillStatuses(CorrespondentBillStatusCriteria criteria) {
        log.debug("REST request to count CorrespondentBillStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentBillStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondent-bill-statuses/:id} : get the "id" correspondentBillStatus.
     *
     * @param id the id of the correspondentBillStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentBillStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-bill-statuses/{id}")
    public ResponseEntity<CorrespondentBillStatusDTO> getCorrespondentBillStatus(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentBillStatus : {}", id);
        Optional<CorrespondentBillStatusDTO> correspondentBillStatusDTO = correspondentBillStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentBillStatusDTO);
    }

    /**
     * {@code DELETE  /correspondent-bill-statuses/:id} : delete the "id" correspondentBillStatus.
     *
     * @param id the id of the correspondentBillStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-bill-statuses/{id}")
    public ResponseEntity<Void> deleteCorrespondentBillStatus(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentBillStatus : {}", id);
        correspondentBillStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
