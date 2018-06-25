package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.repository.utility.ExcelUtility;
import com.cpi.correspondent.repository.utility.JasperReportUtility;
import com.cpi.correspondent.service.CPICorrespondentQueryService;
import com.cpi.correspondent.service.CPICorrespondentService;
import com.cpi.correspondent.service.ExcelService;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.web.bean.CPICorrespondentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * REST controller for managing CPICorrespondent.
 */
@RestController
@RequestMapping("/api")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    private final static  Long EXCEL_TEMPLATE_FOR_1 = new Long(1);

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExcelUtility excelRepository;

    @Autowired
    private JasperReportUtility jasperReportUtility;

    private final CPICorrespondentService cPICorrespondentService;

    private final CPICorrespondentQueryService cPICorrespondentQueryService;

    public TestResource(CPICorrespondentService cPICorrespondentService, CPICorrespondentQueryService cPICorrespondentQueryService) {
        this.cPICorrespondentService = cPICorrespondentService;
        this.cPICorrespondentQueryService = cPICorrespondentQueryService;
    }

    @GetMapping("/test/pdf")
    @Timed
    public ResponseEntity<byte[]> processPDF()  {
        log.debug("REST request to upload excel xls file for parse ");

        Map<String, Object> parameter = new HashMap<String, Object>();
//        parameter.putAll(jasperReportUtility.addImageMapParamete("reports", "cherry.jpg", "cherryImage"));
        ResponseEntity<byte[]> responseEntity = jasperReportUtility.processPDF(1, parameter);

        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=11.pdf");

        return new ResponseEntity<>(responseEntity.getBody(), headers, HttpStatus.OK);
    }


    @GetMapping("/test/statistics")
    @Timed
    public ResponseEntity<byte[]> getStatsForCPICorrespondents() {
        Page<CPICorrespondentDTO> cpiCorrespondentDTOS = cPICorrespondentService.findAll(null);
        List<CPICorrespondentBean> cpiCorrespondentBeans = new ArrayList<>();
        for (CPICorrespondentDTO cpiCorrespondentDTO : cpiCorrespondentDTOS) {
            CPICorrespondentBean cpiCorrespondentBean = new CPICorrespondentBean();
            cpiCorrespondentBean.init(cpiCorrespondentDTO);
            cpiCorrespondentBeans.add(cpiCorrespondentBean);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("results", cpiCorrespondentBeans);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        if (cpiCorrespondentBeans.size() > 0) {
//            outputStream = excelService.exportExcelFromTemplate("reports/StatsforCorrespondent.xlsx", map);
//        }

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        map.put("jxlid", EXCEL_TEMPLATE_FOR_1);
        if (cpiCorrespondentBeans.size() > 0) {
            responseEntity = excelRepository.processExcel(map);
//            body = responseEntity.getBody();
        }

        StringBuilder fileName = new StringBuilder();
        fileName.append("\"Stats_for_Correspondent").append(".xlsx\"");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + fileName.toString());
//        header.setContentLength(outputStream.toByteArray().length);
//
//        return new ResponseEntity<>(outputStream.toByteArray(), header, HttpStatus.OK);
        header.setContentLength(responseEntity.getBody().length);

        return new ResponseEntity<>(responseEntity.getBody(), header, HttpStatus.OK);
    }
}
