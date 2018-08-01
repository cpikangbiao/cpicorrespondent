package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.repository.CorrespondentBillRepository;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.repository.common.CurrencyRepository;
import com.cpi.correspondent.repository.utility.JasperReportUtility;
import com.cpi.correspondent.service.CorrespondentBillService;
import com.cpi.correspondent.service.dto.common.CurrencyDTO;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillCriteria;
import com.cpi.correspondent.service.CorrespondentBillQueryService;
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

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * REST controller for managing CorrespondentBill.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentBillResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentBillResource.class);

    private static final int CORRESPONDENT_FEE_TYPE_CORRESPONDENT = 5;

    private static final int CORRESPONDENT_FEE_TYPE_OTHER = 4;

    private static final int CORRESPONDENT_FEE_TYPE_SURVEYOR = 1;

    private static final int CORRESPONDENT_FEE_TYPE_LAWAY = 2;

    private static final int CORRESPONDENT_FEE_TYPE_EXPERT = 3;

    private static final int CORRESPONDENT_FEE_TYPE_BAIL = 6;

    private static final String ENTITY_NAME = "correspondentBill";

    private static final Integer CORR_BILL_PDF_TEMPLATE_CREDIT = 1;

    private static final Integer CORR_BILL_PDF_TEMPLATE_DEBIT = 2;

    @Autowired
    private JasperReportUtility jasperReportUtility;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CorrespondentBillRepository correspondentBillRepository;

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    private final CorrespondentBillService correspondentBillService;

    private final CorrespondentBillQueryService correspondentBillQueryService;

    public CorrespondentBillResource(CorrespondentBillService correspondentBillService, CorrespondentBillQueryService correspondentBillQueryService) {
        this.correspondentBillService = correspondentBillService;
        this.correspondentBillQueryService = correspondentBillQueryService;
    }

    /**
     * POST  /correspondent-bills : Create a new correspondentBill.
     *
     * @param correspondentBillDTO the correspondentBillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentBillDTO, or with status 400 (Bad Request) if the correspondentBill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-bills")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> createCorrespondentBill(@RequestBody CorrespondentBillDTO correspondentBillDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentBill : {}", correspondentBillDTO);
        if (correspondentBillDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentBill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentBillDTO result = correspondentBillService.save(correspondentBillDTO);
        return ResponseEntity.created(new URI("/api/correspondent-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-bills : Updates an existing correspondentBill.
     *
     * @param correspondentBillDTO the correspondentBillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentBillDTO,
     * or with status 400 (Bad Request) if the correspondentBillDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentBillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-bills")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> updateCorrespondentBill(@RequestBody CorrespondentBillDTO correspondentBillDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentBill : {}", correspondentBillDTO);
        if (correspondentBillDTO.getId() == null) {
            return createCorrespondentBill(correspondentBillDTO);
        }
        CorrespondentBillDTO result = correspondentBillService.save(correspondentBillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentBillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-bills : get all the correspondentBills.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentBills in body
     */
    @GetMapping("/correspondent-bills")
    @Timed
    public ResponseEntity<List<CorrespondentBillDTO>> getAllCorrespondentBills(CorrespondentBillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentBills by criteria: {}", criteria);
        Page<CorrespondentBillDTO> page = correspondentBillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-bills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-bills/:id : get the "id" correspondentBill.
     *
     * @param id the id of the correspondentBillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentBillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-bills/{id}")
    @Timed
    public ResponseEntity<CorrespondentBillDTO> getCorrespondentBill(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentBill : {}", id);
        CorrespondentBillDTO correspondentBillDTO = correspondentBillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentBillDTO));
    }

    /**
     * DELETE  /correspondent-bills/:id : delete the "id" correspondentBill.
     *
     * @param id the id of the correspondentBillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-bills/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentBill(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentBill : {}", id);
        correspondentBillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/correspondent-bills/pdf/{id}")
    @Timed
    public ResponseEntity<byte[]> getPDFFileForBill(@PathVariable Long id) {
        CorrespondentBill correspondentBill = correspondentBillRepository.findOne(id);
        ResponseEntity<byte[]> responseEntity  = new ResponseEntity(HttpStatus.OK);
        if (correspondentBill.getBillFinanceType().getId().equals(BillFinanceType.BILL_FINANCE_TYPE_CREDIT)) {
            responseEntity  = jasperReportUtility.processPDF(CORR_BILL_PDF_TEMPLATE_CREDIT, createCreditBillMap(correspondentBill));
        } else if (correspondentBill.getBillFinanceType().getId().equals(BillFinanceType.BILL_FINANCE_TYPE_DEBIT)) {
//            responseEntity  = jasperReportUtility.processPDF("HullCorrespondentDebitBill.jasper", createDebitBillMap(correspondentBill));
            responseEntity  = jasperReportUtility.processPDF(CORR_BILL_PDF_TEMPLATE_DEBIT, createDebitBillMap(correspondentBill));
        }


        StringBuilder fileName = new StringBuilder();
        fileName.append("\"Correspondent_Bill").append(".pdf\"");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/pdf"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.toString());

        header.setContentLength(responseEntity.getBody().length);

        return new ResponseEntity<>(responseEntity.getBody(), header, HttpStatus.OK);
    }

    private Map<String, Object> createCreditBillMap(CorrespondentBill correspondentbill) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("receiver", correspondentbill.getReceiver());
        map.put("DebitNoteCode", correspondentbill.getCorrespondentBillCode());

        if (correspondentbill.getCorrespondentBillDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(correspondentbill.getCorrespondentBillDate(), ZoneId.systemDefault());
            map.put("DebitDate", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (correspondentbill.getCpiCorrespondent() != null) {
            map.put("cpiRef", correspondentbill.getCpiCorrespondent().getCorrespondentCode());
            map.put("clientRef", correspondentbill.getCpiCorrespondent().getClientRef());
        }
        if (correspondentbill.getCredit() != null) {
            map.put("bankName", correspondentbill.getCredit().getBankName());
            map.put("bankAccount", correspondentbill.getCredit().getAccountNo());
        }


        if (correspondentbill.getExchangeCurrency() != null) {
            CurrencyDTO currencyDTO = currencyRepository.findCurrencyByID(correspondentbill.getExchangeCurrency());
            map.put("currency", currencyDTO.getNameAbbre());
            map.put("amount", correspondentbill.getExchangeAmount());
        }

        map.put("mv", correspondentbill.getRemark());

        return map;
    }

    private Map<String, Object> createDebitBillMap(CorrespondentBill correspondentbill) {
        Map<String, Object> parameter = new HashMap<String, Object>();

        parameter.put("reciever", correspondentbill.getReceiver());
        parameter.put("DebitNoteCode", correspondentbill.getCorrespondentBillCode());

        if (correspondentbill.getCorrespondentBillDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(correspondentbill.getCorrespondentBillDate(), ZoneId.systemDefault());
            parameter.put("DebitDate", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (correspondentbill.getCpiCorrespondent().getClub() != null) {
            parameter.put("co", correspondentbill.getCpiCorrespondent().getClub().getClubName());
        }

        if (correspondentbill.getCpiCorrespondent() != null) {
            parameter.put("cpiRef", correspondentbill.getCpiCorrespondent().getCorrespondentCode());
            parameter.put("yourRef", correspondentbill.getCpiCorrespondent().getClientRef());

            if (correspondentbill.getCpiCorrespondent().getClubPerson() != null) {
                parameter.put("clientRef", correspondentbill.getCpiCorrespondent().getClubPerson().getClubPersonName());
            }

            parameter.put("attition", correspondentbill.getCpiCorrespondent().getClub().getClubName());
        }
        if (correspondentbill.getCpiCorrespondent().getClubPerson() != null) {
            parameter.put("attition", correspondentbill.getCpiCorrespondent().getClubPerson().getClubPersonName());
            parameter.put("email", correspondentbill.getCpiCorrespondent().getClubPerson().getEmail());
        }

        parameter.put("mv", correspondentbill.getRemark());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringBuilder surveyFeeDetailString = new StringBuilder();
        StringBuilder serviceFeeDetailString = new StringBuilder();
        StringBuilder otherFeeDetailString = new StringBuilder();

        BigDecimal surveyFeeSum = new BigDecimal(0.0);
        BigDecimal serviceFeeSum = new BigDecimal(0.0);
        BigDecimal otherFeeSum = new BigDecimal(0.0);
        BigDecimal sumAll = new BigDecimal(0.0);

        List<CorrespondentFeeAndBill> correspondentFeeAndBills = correspondentFeeAndBillRepository.findAllByCorrespondentDebitBill(correspondentbill);
        for (CorrespondentFeeAndBill correspondentFeeAndBill : correspondentFeeAndBills) {
            CorrespondentFee correspondentFee = correspondentFeeAndBill.getCorrespondentFee();
            switch (correspondentFee.getCorrespondentFeeType().getId().intValue()) {
                case CORRESPONDENT_FEE_TYPE_OTHER: { //for other
                    otherFeeDetailString.append(decimalFormat.format(correspondentFee.getCostDollar()));
                    if (correspondentFee.getRemark() != null && correspondentFee.getRemark().length() > 0) {
                        otherFeeDetailString.append("(").append(correspondentFee.getRemark()).append(")");
                    }
                    otherFeeDetailString.append("\n");
                    otherFeeSum = otherFeeSum.add(correspondentFee.getCostDollar());
                    }
                    break;
                case CORRESPONDENT_FEE_TYPE_CORRESPONDENT: //Correspondent Fee
                    {
                        serviceFeeDetailString.append(decimalFormat.format(correspondentFee.getCostDollar()));
                        if (correspondentFee.getRemark() != null && correspondentFee.getRemark().length() > 0) {
                            serviceFeeDetailString.append("(").append(correspondentFee.getRemark()).append(")");
                        }
                        serviceFeeDetailString.append("\n");
                        serviceFeeSum = serviceFeeSum.add(correspondentFee.getCostDollar());
                    }
                        break;

                case CORRESPONDENT_FEE_TYPE_SURVEYOR:
                case CORRESPONDENT_FEE_TYPE_LAWAY:
                case CORRESPONDENT_FEE_TYPE_EXPERT:
                case CORRESPONDENT_FEE_TYPE_BAIL:
                default:
                     {
                        surveyFeeDetailString.append(decimalFormat.format(correspondentFee.getCostDollar()));
                        if (correspondentFee.getRemark() != null && correspondentFee.getRemark().length() > 0) {
                            surveyFeeDetailString.append("(").append(correspondentFee.getRemark()).append(")");
                        }
                        surveyFeeDetailString.append("\n");
                        surveyFeeSum = surveyFeeSum.add(correspondentFee.getCostDollar());
                    }
                    break;
            }

//            if (correspondentFee.getCorrespondentFeeType().getId().equals(CorrespondentFeeType.CORRESPONDENT_FEE_TYPE_OTHER)) {
//                otherFeeDetailString.append(decimalFormat.format(correspondentFee.getCostDollar()));
//                if (correspondentFee.getRemark() != null && correspondentFee.getRemark().length() > 0) {
//                    otherFeeDetailString.append("(").append(correspondentFee.getRemark()).append(")");
//                }
//                otherFeeDetailString.append("\n");
//                otherFeeSum = otherFeeSum.add(correspondentFee.getCostDollar());
//            } else if (correspondentFee.getCorrespondentFeeType().getId().equals(CorrespondentFeeType.CORRESPONDENT_FEE_TYPE_CORRESPONDENT)) {
//                serviceFeeDetailString.append(decimalFormat.format(correspondentFee.getCostDollar()));
//                if (correspondentFee.getRemark() != null && correspondentFee.getRemark().length() > 0) {
//                    serviceFeeDetailString.append("(").append(correspondentFee.getRemark()).append(")");
//                }
//                serviceFeeDetailString.append("\n");
//                serviceFeeSum = serviceFeeSum.add(correspondentFee.getCostDollar());
//            } else {
//                surveyFeeDetailString.append(decimalFormat.format(correspondentFee.getCostDollar()));
//                if (correspondentFee.getRemark() != null && correspondentFee.getRemark().length() > 0) {
//                    surveyFeeDetailString.append("(").append(correspondentFee.getRemark()).append(")");
//                }
//                surveyFeeDetailString.append("\n");
//                surveyFeeSum = surveyFeeSum.add(correspondentFee.getCostDollar());
//            }

        }

        parameter.put("surveyFeeDetail", surveyFeeDetailString.toString());
        parameter.put("surveyFeeSum", decimalFormat.format(surveyFeeSum));

        parameter.put("serviceFeeDetail", serviceFeeDetailString.toString());
        parameter.put("serviceFeeSum", decimalFormat.format(serviceFeeSum));

        parameter.put("otherFeeDetail", otherFeeDetailString.toString());
        parameter.put("otherFeeSum", decimalFormat.format(otherFeeSum));

        sumAll = sumAll.add(surveyFeeSum).add(serviceFeeSum).add(otherFeeSum);

        parameter.put("sumAll", decimalFormat.format(sumAll));

        LocalDateTime localDateTime = LocalDateTime.ofInstant(correspondentbill.getDueDate(), ZoneId.systemDefault());
        parameter.put("dueDate", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


//        ArrayList<CorrFeeDetailBean> corrFeeDetails = new ArrayList<CorrFeeDetailBean>();
//        List<CorrFeeVO> corrFees = new CorrFeeAndBillDAO().getCorrFee(correspondentbill);
//        for (CorrFeeVO corrFee : corrFees) {
//            CorrFeeDetailBean corrFeeDetailBean = new CorrFeeDetailBean();
//            corrFeeDetailBean.init(corrFee);
//            corrFeeDetails.add(corrFeeDetailBean);
//        }
//        JRBeanCollectionDataSource dataSet_en = new JRBeanCollectionDataSource(corrFeeDetails);
//        parameter.put("details", dataSet_en);

        return parameter;
    }
}
