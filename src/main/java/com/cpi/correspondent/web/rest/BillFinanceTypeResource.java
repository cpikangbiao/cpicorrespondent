package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.service.BillFinanceTypeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;
import com.cpi.correspondent.service.dto.BillFinanceTypeCriteria;
import com.cpi.correspondent.service.BillFinanceTypeQueryService;

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
 * REST controller for managing {@link com.cpi.correspondent.domain.BillFinanceType}.
 */
@RestController
@RequestMapping("/api")
public class BillFinanceTypeResource {

    private final Logger log = LoggerFactory.getLogger(BillFinanceTypeResource.class);

    private static final String ENTITY_NAME = "cpicorrespondentBillFinanceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillFinanceTypeService billFinanceTypeService;

    private final BillFinanceTypeQueryService billFinanceTypeQueryService;

    public BillFinanceTypeResource(BillFinanceTypeService billFinanceTypeService, BillFinanceTypeQueryService billFinanceTypeQueryService) {
        this.billFinanceTypeService = billFinanceTypeService;
        this.billFinanceTypeQueryService = billFinanceTypeQueryService;
    }

    /**
     * {@code POST  /bill-finance-types} : Create a new billFinanceType.
     *
     * @param billFinanceTypeDTO the billFinanceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billFinanceTypeDTO, or with status {@code 400 (Bad Request)} if the billFinanceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bill-finance-types")
    public ResponseEntity<BillFinanceTypeDTO> createBillFinanceType(@Valid @RequestBody BillFinanceTypeDTO billFinanceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BillFinanceType : {}", billFinanceTypeDTO);
        if (billFinanceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new billFinanceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillFinanceTypeDTO result = billFinanceTypeService.save(billFinanceTypeDTO);
        return ResponseEntity.created(new URI("/api/bill-finance-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bill-finance-types} : Updates an existing billFinanceType.
     *
     * @param billFinanceTypeDTO the billFinanceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billFinanceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the billFinanceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billFinanceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bill-finance-types")
    public ResponseEntity<BillFinanceTypeDTO> updateBillFinanceType(@Valid @RequestBody BillFinanceTypeDTO billFinanceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BillFinanceType : {}", billFinanceTypeDTO);
        if (billFinanceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillFinanceTypeDTO result = billFinanceTypeService.save(billFinanceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billFinanceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bill-finance-types} : get all the billFinanceTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billFinanceTypes in body.
     */
    @GetMapping("/bill-finance-types")
    public ResponseEntity<List<BillFinanceTypeDTO>> getAllBillFinanceTypes(BillFinanceTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get BillFinanceTypes by criteria: {}", criteria);
        Page<BillFinanceTypeDTO> page = billFinanceTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /bill-finance-types/count} : count all the billFinanceTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/bill-finance-types/count")
    public ResponseEntity<Long> countBillFinanceTypes(BillFinanceTypeCriteria criteria) {
        log.debug("REST request to count BillFinanceTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(billFinanceTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bill-finance-types/:id} : get the "id" billFinanceType.
     *
     * @param id the id of the billFinanceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billFinanceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bill-finance-types/{id}")
    public ResponseEntity<BillFinanceTypeDTO> getBillFinanceType(@PathVariable Long id) {
        log.debug("REST request to get BillFinanceType : {}", id);
        Optional<BillFinanceTypeDTO> billFinanceTypeDTO = billFinanceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billFinanceTypeDTO);
    }

    /**
     * {@code DELETE  /bill-finance-types/:id} : delete the "id" billFinanceType.
     *
     * @param id the id of the billFinanceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bill-finance-types/{id}")
    public ResponseEntity<Void> deleteBillFinanceType(@PathVariable Long id) {
        log.debug("REST request to delete BillFinanceType : {}", id);
        billFinanceTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
