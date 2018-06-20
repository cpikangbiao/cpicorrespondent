package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentFeeTypeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeCriteria;
import com.cpi.correspondent.service.CorrespondentFeeTypeQueryService;
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
 * REST controller for managing CorrespondentFeeType.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentFeeTypeResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentFeeTypeResource.class);

    private static final String ENTITY_NAME = "correspondentFeeType";

    private final CorrespondentFeeTypeService correspondentFeeTypeService;

    private final CorrespondentFeeTypeQueryService correspondentFeeTypeQueryService;

    public CorrespondentFeeTypeResource(CorrespondentFeeTypeService correspondentFeeTypeService, CorrespondentFeeTypeQueryService correspondentFeeTypeQueryService) {
        this.correspondentFeeTypeService = correspondentFeeTypeService;
        this.correspondentFeeTypeQueryService = correspondentFeeTypeQueryService;
    }

    /**
     * POST  /correspondent-fee-types : Create a new correspondentFeeType.
     *
     * @param correspondentFeeTypeDTO the correspondentFeeTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentFeeTypeDTO, or with status 400 (Bad Request) if the correspondentFeeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-fee-types")
    @Timed
    public ResponseEntity<CorrespondentFeeTypeDTO> createCorrespondentFeeType(@Valid @RequestBody CorrespondentFeeTypeDTO correspondentFeeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentFeeType : {}", correspondentFeeTypeDTO);
        if (correspondentFeeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentFeeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentFeeTypeDTO result = correspondentFeeTypeService.save(correspondentFeeTypeDTO);
        return ResponseEntity.created(new URI("/api/correspondent-fee-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-fee-types : Updates an existing correspondentFeeType.
     *
     * @param correspondentFeeTypeDTO the correspondentFeeTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentFeeTypeDTO,
     * or with status 400 (Bad Request) if the correspondentFeeTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentFeeTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-fee-types")
    @Timed
    public ResponseEntity<CorrespondentFeeTypeDTO> updateCorrespondentFeeType(@Valid @RequestBody CorrespondentFeeTypeDTO correspondentFeeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentFeeType : {}", correspondentFeeTypeDTO);
        if (correspondentFeeTypeDTO.getId() == null) {
            return createCorrespondentFeeType(correspondentFeeTypeDTO);
        }
        CorrespondentFeeTypeDTO result = correspondentFeeTypeService.save(correspondentFeeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentFeeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-fee-types : get all the correspondentFeeTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentFeeTypes in body
     */
    @GetMapping("/correspondent-fee-types")
    @Timed
    public ResponseEntity<List<CorrespondentFeeTypeDTO>> getAllCorrespondentFeeTypes(CorrespondentFeeTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentFeeTypes by criteria: {}", criteria);
        Page<CorrespondentFeeTypeDTO> page = correspondentFeeTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-fee-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-fee-types/:id : get the "id" correspondentFeeType.
     *
     * @param id the id of the correspondentFeeTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentFeeTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-fee-types/{id}")
    @Timed
    public ResponseEntity<CorrespondentFeeTypeDTO> getCorrespondentFeeType(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentFeeType : {}", id);
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentFeeTypeDTO));
    }

    /**
     * DELETE  /correspondent-fee-types/:id : delete the "id" correspondentFeeType.
     *
     * @param id the id of the correspondentFeeTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-fee-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentFeeType(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentFeeType : {}", id);
        correspondentFeeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
