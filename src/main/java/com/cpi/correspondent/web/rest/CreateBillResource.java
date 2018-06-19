package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;
import com.cpi.correspondent.service.CorrespondentFeeService;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeCriteria;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorrespondentFee.
 */
@RestController
@RequestMapping("/api")
public class CreateBillResource {

    private final Logger log = LoggerFactory.getLogger(CreateBillResource.class);

    private final CorrespondentFeeService correspondentFeeService;

    private final CorrespondentFeeQueryService correspondentFeeQueryService;

    public CreateBillResource(CorrespondentFeeService correspondentFeeService, CorrespondentFeeQueryService correspondentFeeQueryService) {
        this.correspondentFeeService = correspondentFeeService;
        this.correspondentFeeQueryService = correspondentFeeQueryService;
    }

    /**
     * POST  /correspondent-fees : Create a new correspondentFee.
     *
     * @param correspondentFeeDTO the correspondentFeeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentFeeDTO, or with status 400 (Bad Request) if the correspondentFee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PostMapping("/create-credit-bill")
//    @Timed
//    public ResponseEntity<CorrespondentBillDTO> createCreditBill(@RequestBody List<Long> ids) throws URISyntaxException {
//        log.debug("REST request to create credit bill for fees : {}", ids);
//
//        if ( ids.size() > 0 ) {
////            throw new BadRequestAlertException("A new correspondentFee cannot already have an ID", ENTITY_NAME, "idexists");
//        }
////        CorrespondentFeeDTO result = correspondentFeeService.save(correspondentFeeDTO);
//        return ResponseEntity.created(new URI("/api/correspondent-fees/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }


}
