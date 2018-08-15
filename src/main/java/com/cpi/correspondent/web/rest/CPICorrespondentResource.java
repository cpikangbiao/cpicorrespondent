package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.repository.CorrespondentFeeRepository;
import com.cpi.correspondent.repository.common.UserRepository;
import com.cpi.correspondent.repository.other.MonthCountStatistics;
import com.cpi.correspondent.repository.other.TypeCountStatistics;
import com.cpi.correspondent.repository.other.YearCountStatistics;
import com.cpi.correspondent.repository.utility.ExcelUtility;
import com.cpi.correspondent.service.*;
import com.cpi.correspondent.service.bean.CpiCorrespondentTimeLineService;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.web.bean.CPICorrespondentBean;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;
import io.github.jhipster.web.util.ResponseUtil;
import org.checkerframework.checker.units.qual.A;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing CPICorrespondent.
 */
@RestController
@RequestMapping("/api")
public class CPICorrespondentResource {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentResource.class);

    private final static  Long EXCEL_TEMPLATE_FOR_1 = new Long(1);

    private static final String ENTITY_NAME = "cPICorrespondent";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CorrespondentFeeQueryService correspondentFeeQueryService;

    @Autowired
    private CorrespondentBillQueryService correspondentBillQueryService;

    @Autowired
    private ExcelUtility excelRepository;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private CpiCorrespondentTimeLineService cpiCorrespondentTimeLineService;

    private final CPICorrespondentService cPICorrespondentService;

    private final CPICorrespondentQueryService cPICorrespondentQueryService;

    public CPICorrespondentResource(CPICorrespondentService cPICorrespondentService, CPICorrespondentQueryService cPICorrespondentQueryService) {
        this.cPICorrespondentService = cPICorrespondentService;
        this.cPICorrespondentQueryService = cPICorrespondentQueryService;
    }

    /**
     * POST  /cpi-correspondents : Create a new cPICorrespondent.
     *
     * @param cPICorrespondentDTO the cPICorrespondentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cPICorrespondentDTO, or with status 400 (Bad Request) if the cPICorrespondent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cpi-correspondents")
    @Timed
    public ResponseEntity<CPICorrespondentDTO> createCPICorrespondent(@Valid @RequestBody CPICorrespondentDTO cPICorrespondentDTO) throws URISyntaxException {
        log.debug("REST request to save CPICorrespondent : {}", cPICorrespondentDTO);
        if (cPICorrespondentDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPICorrespondent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPICorrespondentDTO result = cPICorrespondentService.save(cPICorrespondentDTO);
        return ResponseEntity.created(new URI("/api/cpi-correspondents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cpi-correspondents : Updates an existing cPICorrespondent.
     *
     * @param cPICorrespondentDTO the cPICorrespondentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cPICorrespondentDTO,
     * or with status 400 (Bad Request) if the cPICorrespondentDTO is not valid,
     * or with status 500 (Internal Server Error) if the cPICorrespondentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cpi-correspondents")
    @Timed
    public ResponseEntity<CPICorrespondentDTO> updateCPICorrespondent(@Valid @RequestBody CPICorrespondentDTO cPICorrespondentDTO) throws URISyntaxException {
        log.debug("REST request to update CPICorrespondent : {}", cPICorrespondentDTO);
        if (cPICorrespondentDTO.getId() == null) {
            return createCPICorrespondent(cPICorrespondentDTO);
        }
        CPICorrespondentDTO result = cPICorrespondentService.save(cPICorrespondentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cPICorrespondentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cpi-correspondents : get all the cPICorrespondents.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cPICorrespondents in body
     */
    @GetMapping("/cpi-correspondents")
    @Timed
    public ResponseEntity<List<CPICorrespondentDTO>> getAllCPICorrespondents(CPICorrespondentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPICorrespondents by criteria: {}", criteria);
        Page<CPICorrespondentDTO> page = cPICorrespondentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cpi-correspondents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cpi-correspondents/:id : get the "id" cPICorrespondent.
     *
     * @param id the id of the cPICorrespondentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cPICorrespondentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cpi-correspondents/{id}")
    @Timed
    public ResponseEntity<CPICorrespondentDTO> getCPICorrespondent(@PathVariable Long id) {
        log.debug("REST request to get CPICorrespondent : {}", id);
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cPICorrespondentDTO));
    }

    /**
     * DELETE  /cpi-correspondents/:id : delete the "id" cPICorrespondent.
     *
     * @param id the id of the cPICorrespondentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cpi-correspondents/{id}")
    @Timed
    public ResponseEntity<Void> deleteCPICorrespondent(@PathVariable Long id) {
        log.debug("REST request to delete CPICorrespondent : {}", id);
        cPICorrespondentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
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

    @GetMapping("/cpi-correspondents/timeline/{id}")
    @Timed
    public ResponseEntity<List> getTimeline(@PathVariable Long id) {
        return new ResponseEntity<>(cpiCorrespondentTimeLineService.getCpiCorrespondentTimeLineBeans(id), HttpStatus.OK);
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
