package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.repository.common.UserRepository;
import com.cpi.correspondent.repository.utility.ExcelRepository;
import com.cpi.correspondent.service.CPICorrespondentQueryService;
import com.cpi.correspondent.service.CPICorrespondentService;
import com.cpi.correspondent.service.CorrespondentBillQueryService;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;
import com.cpi.correspondent.service.bean.timeline.CpiCorrespondentTimeLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing CPICorrespondent.
 */
@RestController
@RequestMapping("/api")
public class CPICorrespondentTimeLineResource {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentTimeLineResource.class);

    private final static  Long EXCEL_TEMPLATE_FOR_1 = new Long(1);

    private static final String ENTITY_NAME = "cPICorrespondent";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CorrespondentFeeQueryService correspondentFeeQueryService;

    @Autowired
    private CorrespondentBillQueryService correspondentBillQueryService;

    @Autowired
    private ExcelRepository excelRepository;

    @Autowired
    private CpiCorrespondentTimeLineService cpiCorrespondentTimeLineService;

    private final CPICorrespondentService cPICorrespondentService;

    private final CPICorrespondentQueryService cPICorrespondentQueryService;

    public CPICorrespondentTimeLineResource(CPICorrespondentService cPICorrespondentService, CPICorrespondentQueryService cPICorrespondentQueryService) {
        this.cPICorrespondentService = cPICorrespondentService;
        this.cPICorrespondentQueryService = cPICorrespondentQueryService;
    }

    @GetMapping("/cpi-correspondents/timeline/{id}")
    @Timed
    public ResponseEntity<List> getTimeline(@PathVariable Long id) {
        return new ResponseEntity<>(cpiCorrespondentTimeLineService.getCpiCorrespondentTimeLineBeans(id), HttpStatus.OK);
    }


}
