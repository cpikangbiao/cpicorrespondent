package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.repository.CorrespondentBillRepository;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.repository.common.CurrencyRepository;
import com.cpi.correspondent.repository.utility.JasperReportRepository;

import com.cpi.correspondent.service.CorrespondentBillService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillCriteria;
import com.cpi.correspondent.service.CorrespondentBillQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

import java.util.*;

/**
 * REST controller for managing {@link com.cpi.correspondent.domain.CorrespondentBill}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillResource.class);

    private static final int CORRESPONDENT_FEE_TYPE_CORRESPONDENT = 5;

    private static final int CORRESPONDENT_FEE_TYPE_OTHER = 4;

    private static final int CORRESPONDENT_FEE_TYPE_SURVEYOR = 1;

    private static final int CORRESPONDENT_FEE_TYPE_LAWAY = 2;

    private static final int CORRESPONDENT_FEE_TYPE_EXPERT = 3;

    private static final int CORRESPONDENT_FEE_TYPE_BAIL = 6;


    private static final String ENTITY_NAME = "cpicorrespondentCorrespondentBill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final Integer CORR_BILL_PDF_TEMPLATE_CREDIT = 1;

    private static final Integer CORR_BILL_PDF_TEMPLATE_DEBIT = 2;

    @Autowired
    private JasperReportRepository jasperReportRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CorrespondentBillRepository correspondentBillRepository;

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    private final CorrespondentBillService correspondentBillService;

    private final CorrespondentBillQueryService correspondentBillQueryService;

    public CorrespondentBillResource(CorrespondentBillService correspondentBillService, CorrespondentBillQueryService correspondentBillQueryService) {
        this.correspondentBillService = correspondentBillService;
        this.correspondentBillQueryService = correspondentBillQueryService;
    }

    /**
     * {@code POST  /correspondent-bills} : Create a new correspondentBill.
     *
     * @param correspondentBillDTO the correspondentBillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentBillDTO, or with status {@code 400 (Bad Request)} if the correspondentBill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-bills")
    public ResponseEntity<CorrespondentBillDTO> createCorrespondentBill(@RequestBody CorrespondentBillDTO correspondentBillDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentBill : {}", correspondentBillDTO);
        if (correspondentBillDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentBill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentBillDTO result = correspondentBillService.save(correspondentBillDTO);
        return ResponseEntity.created(new URI("/api/correspondent-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-bills} : Updates an existing correspondentBill.
     *
     * @param correspondentBillDTO the correspondentBillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentBillDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentBillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentBillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-bills")
    public ResponseEntity<CorrespondentBillDTO> updateCorrespondentBill(@RequestBody CorrespondentBillDTO correspondentBillDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentBill : {}", correspondentBillDTO);
        if (correspondentBillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentBillDTO result = correspondentBillService.save(correspondentBillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentBillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-bills} : get all the correspondentBills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentBills in body.
     */
    @GetMapping("/correspondent-bills")
    public ResponseEntity<List<CorrespondentBillDTO>> getAllCorrespondentBills(CorrespondentBillCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentBills by criteria: {}", criteria);
        Page<CorrespondentBillDTO> page = correspondentBillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-bills/count} : count all the correspondentBills.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-bills/count")
    public ResponseEntity<Long> countCorrespondentBills(CorrespondentBillCriteria criteria) {
        log.debug("REST request to count CorrespondentBills by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentBillQueryService.countByCriteria(criteria));
    }



    /**
     * {@code GET  /correspondent-bills/:id} : get the "id" correspondentBill.
     *
     * @param id the id of the correspondentBillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentBillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-bills/{id}")
    public ResponseEntity<CorrespondentBillDTO> getCorrespondentBill(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentBill : {}", id);
        Optional<CorrespondentBillDTO> correspondentBillDTO = correspondentBillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentBillDTO);
    }

    /**
     * {@code DELETE  /correspondent-bills/:id} : delete the "id" correspondentBill.
     *
     * @param id the id of the correspondentBillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-bills/{id}")
    public ResponseEntity<Void> deleteCorrespondentBill(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentBill : {}", id);
        correspondentBillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
