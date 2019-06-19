package com.cpi.correspondent.web.rest;


import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.repository.CorrespondentFeeRepository;
import com.cpi.correspondent.service.CorrespondentBillCreateService;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillMapper;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing CorrespondentFee.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillCreateResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillCreateResource.class);

    private static final String ENTITY_NAME = "correspondentBill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    @Autowired
    private CorrespondentFeeRepository  correspondentFeeRepository;

    @Autowired
    private CorrespondentBillMapper correspondentBillMapper;

    private final CorrespondentBillCreateService correspondentBillCreateService;

    public CorrespondentBillCreateResource(CorrespondentBillCreateService correspondentBillCreateService) {
        this.correspondentBillCreateService = correspondentBillCreateService;
    }

    @PostMapping("/create-bill/credit")
    public ResponseEntity<CorrespondentBillDTO> createCreditBill(@RequestBody List<Long> ids) throws URISyntaxException {
        log.debug("REST request to create credit bill for fees : {}", ids);

        if ( ids.size() == 0 ) {
            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CorrespondentBillDTO result = correspondentBillCreateService.createCorrespondentBill(ids, BillFinanceType.BILL_FINANCE_TYPE_CREDIT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getCorrespondentBillCode().toString()))
            .body(result);
    }

    @PostMapping("/create-bill/debit")
    public ResponseEntity<CorrespondentBillDTO> createDebitBill(@RequestBody List<Long> ids) throws URISyntaxException {
        log.debug("REST request to create credit bill for fees : {}", ids);

        if ( ids.size() == 0 ) {
            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CorrespondentBillDTO result = correspondentBillCreateService.createCorrespondentBill(ids, BillFinanceType.BILL_FINANCE_TYPE_DEBIT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getCorrespondentBillCode().toString()))
            .body(result);
    }

    @GetMapping("/get-bill-from-fee/credit/{id}")
    public ResponseEntity<CorrespondentBillDTO> getCorrespondentCreditBillFromFee(@PathVariable Long id) {
        log.debug("REST request to get getCorrespondentCreditBillFromFee : {}", id);
        Optional<CorrespondentFee> optional = correspondentFeeRepository.findById(id);

        CorrespondentBillDTO correspondentBillDTO = null;
        if (optional.isPresent()) {
            CorrespondentBill correspondentBill = null;
            List<CorrespondentFeeAndBill> correspondentFeeAndBills = correspondentFeeAndBillRepository.findAllByCorrespondentFee(optional.get());
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
    public ResponseEntity<CorrespondentBillDTO> getCorrespondentDebitBillFromFee(@PathVariable Long id) {
        log.debug("REST request to get getCorrespondentDebitBillFromFee : {}", id);
        Optional<CorrespondentFee> optional = correspondentFeeRepository.findById(id);

        CorrespondentBillDTO correspondentBillDTO = null;
        if (optional.isPresent()) {
            CorrespondentBill correspondentBill = null;
            List<CorrespondentFeeAndBill> correspondentFeeAndBills = correspondentFeeAndBillRepository.findAllByCorrespondentFee(optional.get());
            for (CorrespondentFeeAndBill correspondentFeeAndBill : correspondentFeeAndBills) {
                if (correspondentFeeAndBill.getCorrespondentDebitBill() != null) {
                    correspondentBill = correspondentFeeAndBill.getCorrespondentDebitBill();
                }
            }
            if (correspondentBill != null) {
                correspondentBillDTO = correspondentBillMapper.toDto(correspondentBill);
            }
        }

        return ResponseEntity.ok()
            .body(correspondentBillDTO);
    }


}
