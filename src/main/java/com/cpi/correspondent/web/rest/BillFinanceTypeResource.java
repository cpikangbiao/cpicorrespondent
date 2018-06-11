package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.BillFinanceTypeService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;
import com.cpi.correspondent.service.dto.BillFinanceTypeCriteria;
import com.cpi.correspondent.service.BillFinanceTypeQueryService;
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
 * REST controller for managing BillFinanceType.
 */
@RestController
@RequestMapping("/api")
public class BillFinanceTypeResource {

    private final Logger log = LoggerFactory.getLogger(BillFinanceTypeResource.class);

    private static final String ENTITY_NAME = "billFinanceType";

    private final BillFinanceTypeService billFinanceTypeService;

    private final BillFinanceTypeQueryService billFinanceTypeQueryService;

    public BillFinanceTypeResource(BillFinanceTypeService billFinanceTypeService, BillFinanceTypeQueryService billFinanceTypeQueryService) {
        this.billFinanceTypeService = billFinanceTypeService;
        this.billFinanceTypeQueryService = billFinanceTypeQueryService;
    }

    /**
     * POST  /bill-finance-types : Create a new billFinanceType.
     *
     * @param billFinanceTypeDTO the billFinanceTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billFinanceTypeDTO, or with status 400 (Bad Request) if the billFinanceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bill-finance-types")
    @Timed
    public ResponseEntity<BillFinanceTypeDTO> createBillFinanceType(@Valid @RequestBody BillFinanceTypeDTO billFinanceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BillFinanceType : {}", billFinanceTypeDTO);
        if (billFinanceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new billFinanceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillFinanceTypeDTO result = billFinanceTypeService.save(billFinanceTypeDTO);
        return ResponseEntity.created(new URI("/api/bill-finance-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bill-finance-types : Updates an existing billFinanceType.
     *
     * @param billFinanceTypeDTO the billFinanceTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billFinanceTypeDTO,
     * or with status 400 (Bad Request) if the billFinanceTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the billFinanceTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bill-finance-types")
    @Timed
    public ResponseEntity<BillFinanceTypeDTO> updateBillFinanceType(@Valid @RequestBody BillFinanceTypeDTO billFinanceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BillFinanceType : {}", billFinanceTypeDTO);
        if (billFinanceTypeDTO.getId() == null) {
            return createBillFinanceType(billFinanceTypeDTO);
        }
        BillFinanceTypeDTO result = billFinanceTypeService.save(billFinanceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billFinanceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bill-finance-types : get all the billFinanceTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of billFinanceTypes in body
     */
    @GetMapping("/bill-finance-types")
    @Timed
    public ResponseEntity<List<BillFinanceTypeDTO>> getAllBillFinanceTypes(BillFinanceTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BillFinanceTypes by criteria: {}", criteria);
        Page<BillFinanceTypeDTO> page = billFinanceTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bill-finance-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bill-finance-types/:id : get the "id" billFinanceType.
     *
     * @param id the id of the billFinanceTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billFinanceTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bill-finance-types/{id}")
    @Timed
    public ResponseEntity<BillFinanceTypeDTO> getBillFinanceType(@PathVariable Long id) {
        log.debug("REST request to get BillFinanceType : {}", id);
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(billFinanceTypeDTO));
    }

    /**
     * DELETE  /bill-finance-types/:id : delete the "id" billFinanceType.
     *
     * @param id the id of the billFinanceTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bill-finance-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillFinanceType(@PathVariable Long id) {
        log.debug("REST request to delete BillFinanceType : {}", id);
        billFinanceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
