package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.CorrespondentTypeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;
import com.cpi.correspondent.service.dto.CorrespondentTypeCriteria;
import com.cpi.correspondent.service.CorrespondentTypeQueryService;

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
 * REST controller for managing {@link com.cpi.correspondent.domain.CorrespondentType}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentTypeResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentTypeResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentCorrespondentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentTypeService correspondentTypeService;

    private final CorrespondentTypeQueryService correspondentTypeQueryService;

    public CorrespondentTypeResource(CorrespondentTypeService correspondentTypeService, CorrespondentTypeQueryService correspondentTypeQueryService) {
        this.correspondentTypeService = correspondentTypeService;
        this.correspondentTypeQueryService = correspondentTypeQueryService;
    }

    /**
     * {@code POST  /correspondent-types} : Create a new correspondentType.
     *
     * @param correspondentTypeDTO the correspondentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentTypeDTO, or with status {@code 400 (Bad Request)} if the correspondentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-types")
    public ResponseEntity<CorrespondentTypeDTO> createCorrespondentType(@Valid @RequestBody CorrespondentTypeDTO correspondentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentType : {}", correspondentTypeDTO);
        if (correspondentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentTypeDTO result = correspondentTypeService.save(correspondentTypeDTO);
        return ResponseEntity.created(new URI("/api/correspondent-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-types} : Updates an existing correspondentType.
     *
     * @param correspondentTypeDTO the correspondentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-types")
    public ResponseEntity<CorrespondentTypeDTO> updateCorrespondentType(@Valid @RequestBody CorrespondentTypeDTO correspondentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentType : {}", correspondentTypeDTO);
        if (correspondentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentTypeDTO result = correspondentTypeService.save(correspondentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-types} : get all the correspondentTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentTypes in body.
     */
    @GetMapping("/correspondent-types")
    public ResponseEntity<List<CorrespondentTypeDTO>> getAllCorrespondentTypes(CorrespondentTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentTypes by criteria: {}", criteria);
        Page<CorrespondentTypeDTO> page = correspondentTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-types/count} : count all the correspondentTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-types/count")
    public ResponseEntity<Long> countCorrespondentTypes(CorrespondentTypeCriteria criteria) {
        log.debug("REST request to count CorrespondentTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondent-types/:id} : get the "id" correspondentType.
     *
     * @param id the id of the correspondentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-types/{id}")
    public ResponseEntity<CorrespondentTypeDTO> getCorrespondentType(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentType : {}", id);
        Optional<CorrespondentTypeDTO> correspondentTypeDTO = correspondentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentTypeDTO);
    }

    /**
     * {@code DELETE  /correspondent-types/:id} : delete the "id" correspondentType.
     *
     * @param id the id of the correspondentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-types/{id}")
    public ResponseEntity<Void> deleteCorrespondentType(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentType : {}", id);
        correspondentTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
