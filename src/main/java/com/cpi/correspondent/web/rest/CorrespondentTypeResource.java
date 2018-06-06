package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentTypeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;
import com.cpi.correspondent.service.dto.CorrespondentTypeCriteria;
import com.cpi.correspondent.service.CorrespondentTypeQueryService;
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
 * REST controller for managing CorrespondentType.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentTypeResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentTypeResource.class);

    private static final String ENTITY_NAME = "correspondentType";

    private final CorrespondentTypeService correspondentTypeService;

    private final CorrespondentTypeQueryService correspondentTypeQueryService;

    public CorrespondentTypeResource(CorrespondentTypeService correspondentTypeService, CorrespondentTypeQueryService correspondentTypeQueryService) {
        this.correspondentTypeService = correspondentTypeService;
        this.correspondentTypeQueryService = correspondentTypeQueryService;
    }

    /**
     * POST  /correspondent-types : Create a new correspondentType.
     *
     * @param correspondentTypeDTO the correspondentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentTypeDTO, or with status 400 (Bad Request) if the correspondentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-types")
    @Timed
    public ResponseEntity<CorrespondentTypeDTO> createCorrespondentType(@Valid @RequestBody CorrespondentTypeDTO correspondentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentType : {}", correspondentTypeDTO);
        if (correspondentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentTypeDTO result = correspondentTypeService.save(correspondentTypeDTO);
        return ResponseEntity.created(new URI("/api/correspondent-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-types : Updates an existing correspondentType.
     *
     * @param correspondentTypeDTO the correspondentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentTypeDTO,
     * or with status 400 (Bad Request) if the correspondentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-types")
    @Timed
    public ResponseEntity<CorrespondentTypeDTO> updateCorrespondentType(@Valid @RequestBody CorrespondentTypeDTO correspondentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentType : {}", correspondentTypeDTO);
        if (correspondentTypeDTO.getId() == null) {
            return createCorrespondentType(correspondentTypeDTO);
        }
        CorrespondentTypeDTO result = correspondentTypeService.save(correspondentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-types : get all the correspondentTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentTypes in body
     */
    @GetMapping("/correspondent-types")
    @Timed
    public ResponseEntity<List<CorrespondentTypeDTO>> getAllCorrespondentTypes(CorrespondentTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentTypes by criteria: {}", criteria);
        Page<CorrespondentTypeDTO> page = correspondentTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-types/:id : get the "id" correspondentType.
     *
     * @param id the id of the correspondentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-types/{id}")
    @Timed
    public ResponseEntity<CorrespondentTypeDTO> getCorrespondentType(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentType : {}", id);
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentTypeDTO));
    }

    /**
     * DELETE  /correspondent-types/:id : delete the "id" correspondentType.
     *
     * @param id the id of the correspondentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentType(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentType : {}", id);
        correspondentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
