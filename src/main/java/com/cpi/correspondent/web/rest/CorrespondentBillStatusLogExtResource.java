package com.cpi.correspondent.web.rest;


import com.cpi.correspondent.service.CorrespondentBillStatusLogExtService;
import com.cpi.correspondent.service.CorrespondentBillStatusLogQueryService;
import com.cpi.correspondent.service.CorrespondentBillStatusLogService;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorrespondentBillStatusLog.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillStatusLogExtResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillStatusLogExtResource.class);

    private static final String ENTITY_NAME = "correspondentBillStatusLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CorrespondentBillStatusLogExtService correspondentBillStatusLogExtService;

    public CorrespondentBillStatusLogExtResource() {
    }

    @GetMapping("/correspondent-bill-status-logs/by-bill/{billId}")
    public ResponseEntity<List<CorrespondentBillStatusLogDTO>> getAllCorrespondentBillStatusLogs(@PathVariable Long billId) {
        log.debug("REST request to get CorrespondentBillStatusLogs by billId: {}", billId);
        List<CorrespondentBillStatusLogDTO> page = correspondentBillStatusLogExtService.findByCorrespondentBillId(billId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
