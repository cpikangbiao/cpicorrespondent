package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.repository.utility.ExcelRepository;
import com.cpi.correspondent.service.CPICorrespondentService;
import com.cpi.correspondent.service.ExcelService;
import com.cpi.correspondent.web.bean.CPICorrespondentBean;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;
import com.cpi.correspondent.service.CPICorrespondentQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private ExcelRepository excelRepository;

    @Autowired
    private ExcelService excelService;

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


    @GetMapping("/cpi-correspondents/statistics")
    @Timed
    public ResponseEntity<byte[]> getStatsForCPICorrespondents(CPICorrespondentCriteria criteria) {
        log.debug("REST request to get CPICorrespondents by criteria: {}", criteria);
        List<CPICorrespondentDTO> cpiCorrespondentDTOS = cPICorrespondentQueryService.findByCriteria(criteria);
        List<CPICorrespondentBean> cpiCorrespondentBeans = new ArrayList<>();
        for (CPICorrespondentDTO cpiCorrespondentDTO : cpiCorrespondentDTOS) {
            CPICorrespondentBean cpiCorrespondentBean = new CPICorrespondentBean();
            cpiCorrespondentBean.init(cpiCorrespondentDTO);
            cpiCorrespondentBeans.add(cpiCorrespondentBean);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", cpiCorrespondentBeans);

        byte[] body = null;
//        if (cpiCorrespondentBeans.size() > 0) {
//            body = excelService.exportExcelFromTemplate("StatsForCorrespondentOverview.xlsx", map);
//        }

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        if (cpiCorrespondentBeans.size() > 0) {
            responseEntity = excelRepository.processExcel(EXCEL_TEMPLATE_FOR_1, map);
            body = responseEntity.getBody();
        }

        StringBuilder fileName = new StringBuilder();
        fileName.append("Stats for Correspondent").append(".pdf");

        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=" + fileName.toString());

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
