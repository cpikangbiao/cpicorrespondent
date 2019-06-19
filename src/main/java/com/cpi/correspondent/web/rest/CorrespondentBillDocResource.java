package com.cpi.correspondent.web.rest;


import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.repository.CorrespondentBillRepository;
import com.cpi.correspondent.repository.utility.JasperReportRepository;
import com.cpi.correspondent.service.CorrespondentBillQueryService;
import com.cpi.correspondent.service.CorrespondentBillService;
import com.cpi.correspondent.service.utility.CorrespondentBillParameterGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing CorrespondentBill.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillDocResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillDocResource.class);


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "correspondentBill";

    private static final Integer CORR_BILL_PDF_TEMPLATE_CREDIT = 1;

    private static final Integer CORR_BILL_PDF_TEMPLATE_DEBIT = 2;

    @Autowired
    private JasperReportRepository jasperReportRepository;

    @Autowired
    private CorrespondentBillRepository correspondentBillRepository;

    @Autowired
    private CorrespondentBillParameterGenerator correspondentBillParameterUtility;

    private final CorrespondentBillService correspondentBillService;

    private final CorrespondentBillQueryService correspondentBillQueryService;

    public CorrespondentBillDocResource(CorrespondentBillService correspondentBillService, CorrespondentBillQueryService correspondentBillQueryService) {
        this.correspondentBillService = correspondentBillService;
        this.correspondentBillQueryService = correspondentBillQueryService;
    }

    @GetMapping("/correspondent-bills/pdf/{id}")
    public ResponseEntity<byte[]> getPDFFileForBill(@PathVariable Long id) {
        Optional<CorrespondentBill> optional = correspondentBillRepository.findById(id);
        ResponseEntity<byte[]> responseEntity  = new ResponseEntity(HttpStatus.OK);

        if (optional.isPresent()) {
            CorrespondentBill correspondentBill = optional.get();
            if (correspondentBill.getBillFinanceType().getId().equals(BillFinanceType.BILL_FINANCE_TYPE_CREDIT)) {
                responseEntity  = jasperReportRepository.processPDF(CORR_BILL_PDF_TEMPLATE_CREDIT,
                    correspondentBillParameterUtility.createCreditBillMap(correspondentBill));
            } else if (correspondentBill.getBillFinanceType().getId().equals(BillFinanceType.BILL_FINANCE_TYPE_DEBIT)) {
//            responseEntity  = jasperReportRepository.processPDF("HullCorrespondentDebitBill.jasper", createDebitBillMap(correspondentBill));
                responseEntity  = jasperReportRepository.processPDF(CORR_BILL_PDF_TEMPLATE_DEBIT,
                    correspondentBillParameterUtility.createDebitBillMap(correspondentBill));
            }
        }

        StringBuilder fileName = new StringBuilder();
        fileName.append("\"Correspondent_Bill").append(".pdf\"");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/pdf"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.toString());

        header.setContentLength(responseEntity.getBody().length);

        return new ResponseEntity<>(responseEntity.getBody(), header, HttpStatus.OK);
    }

    //    @GetMapping("/correspondent-bills/debit/duedate")
////    public ResponseEntity<List<CorrespondentBillDTO>> getAllCorrespondentBillsOrderByDueDate(Pageable pageable) {
//        log.debug("REST request to get getAllCorrespondentBillsOrderByDueDate");
//        Page<CorrespondentBillDTO> page = correspondentBillQueryService.findByBillFinanceType(
//                    BillFinanceType.BILL_FINANCE_TYPE_DEBIT,
//                    CorrespondentBillStatus.CORRESPONDENT_BILL_STATUS_NOPAID,
//                    pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api//correspondent-bills/debit/duedate");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }


}
