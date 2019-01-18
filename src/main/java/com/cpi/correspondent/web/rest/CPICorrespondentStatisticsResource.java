package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.repository.common.UserRepository;
import com.cpi.correspondent.repository.other.MonthCountStatistics;
import com.cpi.correspondent.repository.other.TypeCountStatistics;
import com.cpi.correspondent.repository.other.YearCountStatistics;
import com.cpi.correspondent.repository.utility.ExcelRepository;
import com.cpi.correspondent.service.*;
import com.cpi.correspondent.service.bean.timeline.CpiCorrespondentTimeLineService;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.bean.CPICorrespondentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing CPICorrespondent.
 */
@RestController
@RequestMapping("/api")
public class CPICorrespondentStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentStatisticsResource.class);

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

    public CPICorrespondentStatisticsResource(CPICorrespondentService cPICorrespondentService, CPICorrespondentQueryService cPICorrespondentQueryService) {
        this.cPICorrespondentService = cPICorrespondentService;
        this.cPICorrespondentQueryService = cPICorrespondentQueryService;
    }



    @GetMapping("/cpi-correspondents/statistics/year")
    @Timed
    public ResponseEntity<List<YearCountStatistics>> getStatsForGroupByYear() {
        return new ResponseEntity<>(cPICorrespondentQueryService.findYearCountStatistics(), HttpStatus.OK);
    }

    @GetMapping("/cpi-correspondents/statistics/month")
    @Timed
    public ResponseEntity<List<MonthCountStatistics>> getStatsForGroupByMonth() {
        return new ResponseEntity<>(cPICorrespondentQueryService.findMonthCountStatistics(), HttpStatus.OK);
    }

    @GetMapping("/cpi-correspondents/statistics/type")
    @Timed
    public ResponseEntity<List<TypeCountStatistics>> getStatsForType() {
        return new ResponseEntity<>(cPICorrespondentQueryService.findTypeCountStatistics(), HttpStatus.OK);
    }

    @GetMapping("/cpi-correspondents/statistics/club")
    @Timed
    public ResponseEntity<List<TypeCountStatistics>> getStatsForClub() {
        return new ResponseEntity<>(cPICorrespondentQueryService.findClubCountStatistics(), HttpStatus.OK);
    }

    @GetMapping("/cpi-correspondents/statistics")
    @Timed
    public ResponseEntity<byte[]> getStatsForCPICorrespondents(CPICorrespondentCriteria criteria) {
        log.debug("REST request to get CPICorrespondents by criteria: {}", criteria);
        List<CPICorrespondentDTO> cpiCorrespondentDTOS = cPICorrespondentQueryService.findByCriteria(criteria);
        List<CPICorrespondentBean> cpiCorrespondentBeans = new ArrayList<>();
        for (CPICorrespondentDTO cpiCorrespondentDTO : cpiCorrespondentDTOS) {
            CPICorrespondentBean cpiCorrespondentBean = new CPICorrespondentBean();
            cpiCorrespondentBean.init(cpiCorrespondentDTO, userRepository, correspondentFeeQueryService, correspondentBillQueryService);
            cpiCorrespondentBeans.add(cpiCorrespondentBean);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("results", cpiCorrespondentBeans);

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        map.put("jxlid", EXCEL_TEMPLATE_FOR_1);
        if (cpiCorrespondentBeans.size() > 0) {
            responseEntity = excelRepository.processExcel(map);
        }

        StringBuilder fileName = new StringBuilder();
        fileName.append("\"Stats_for_Correspondent").append(".xlsx\"");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.toString());
        if (responseEntity.getBody() != null) {
            header.setContentLength(responseEntity.getBody().length);
        } else {
            header.setContentLength(0);
        }


        return new ResponseEntity<>(responseEntity.getBody(), header, HttpStatus.OK);
    }
}
