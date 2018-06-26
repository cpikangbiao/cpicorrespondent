package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.repository.CorrespondentFeeRepository;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;
import com.cpi.correspondent.service.CorrespondentFeeService;
import com.cpi.correspondent.service.CreateBillService;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeCriteria;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillMapper;
import com.cpi.correspondent.web.bean.CPICorrespondentBean;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.netflix.discovery.converters.Auto;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing CorrespondentFee.
 */
@RestController
@RequestMapping("/api")
public class CreateBillResource {

    private final Logger log = LoggerFactory.getLogger(CreateBillResource.class);

    private static final String ENTITY_NAME = "correspondentBill";

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    @Autowired
    private CorrespondentFeeRepository  correspondentFeeRepository;

    @Autowired
    private CorrespondentBillMapper correspondentBillMapper;

    private final CreateBillService createBillService;

    public CreateBillResource(CreateBillService createBillService) {
        this.createBillService = createBillService;
    }

    @PostMapping("/create-bill/credit")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> createCreditBill(@RequestBody List<Long> ids) throws URISyntaxException {
        log.debug("REST request to create credit bill for fees : {}", ids);

        if ( ids.size() == 0 ) {
            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CorrespondentBillDTO result = createBillService.CreateCorrespondentBill(ids, BillFinanceType.BILL_FINANCE_TYPE_CREDIT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getCorrespondentBillCode().toString()))
            .body(result);
    }

    @PostMapping("/create-bill/debit")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> createDebitBill(@RequestBody List<Long> ids) throws URISyntaxException {
        log.debug("REST request to create credit bill for fees : {}", ids);

        if ( ids.size() == 0 ) {
            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CorrespondentBillDTO result = createBillService.CreateCorrespondentBill(ids, BillFinanceType.BILL_FINANCE_TYPE_DEBIT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getCorrespondentBillCode().toString()))
            .body(result);
    }

    @GetMapping("/get-bill-from-fee/credit/{id}")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> getCorrespondentCreditBillFromFee(@PathVariable Long id) {
        log.debug("REST request to get getCorrespondentCreditBillFromFee : {}", id);
        CorrespondentFee correspondentFee = correspondentFeeRepository.findOne(id);

        CorrespondentBillDTO correspondentBillDTO = null;
        if (correspondentFee != null) {
            CorrespondentBill correspondentBill = null;
            List<CorrespondentFeeAndBill> correspondentFeeAndBills = correspondentFeeAndBillRepository.findAllByCorrespondentFee(correspondentFee);
            for (CorrespondentFeeAndBill correspondentFeeAndBill : correspondentFeeAndBills) {
                if (correspondentFeeAndBill.getCorrespondentCreditBill() != null) {
                    correspondentBill = correspondentFeeAndBill.getCorrespondentCreditBill();
                }
            }
            if (correspondentBill != null) {
                correspondentBillDTO = correspondentBillMapper.toDto(correspondentBill);
            }
        }

        return ResponseEntity.ok()
            .body(correspondentBillDTO);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentBillDTO));
    }

    @GetMapping("/get-bill-from-fee/debit/{id}")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> getCorrespondentDebitBillFromFee(@PathVariable Long id) {
        log.debug("REST request to get getCorrespondentDebitBillFromFee : {}", id);
        CorrespondentFee correspondentFee = correspondentFeeRepository.findOne(id);

        CorrespondentBillDTO correspondentBillDTO = null;
        if (correspondentFee != null) {
            CorrespondentBill correspondentBill = null;
            List<CorrespondentFeeAndBill> correspondentFeeAndBills = correspondentFeeAndBillRepository.findAllByCorrespondentFee(correspondentFee);
            for (CorrespondentFeeAndBill correspondentFeeAndBill : correspondentFeeAndBills) {
                if (correspondentFeeAndBill.getCorrespondentDebitBill() != null) {
                    correspondentBill = correspondentFeeAndBill.getCorrespondentDebitBill();
                }
            }
            if (correspondentBill != null) {
                correspondentBillDTO = correspondentBillMapper.toDto(correspondentBill);
            }
        }

//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentBillDTO));
        return ResponseEntity.ok()
            .body(correspondentBillDTO);
    }


}
