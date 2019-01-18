/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CpiCorrespondentTimeLineBean
 * Author:   admin
 * Date:     2018/8/8 10:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.service.bean.timeline;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.repository.*;
import com.cpi.correspondent.repository.common.CurrencyRepository;
import com.cpi.correspondent.repository.common.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/8/8
 * @since 1.0.0
 */
@Service
public class CpiCorrespondentTimeLineService {

    private final Logger log = LoggerFactory.getLogger(CpiCorrespondentTimeLineService.class);

    private static final String CORRESPONDENT_TIMELINE_TYPE_COMMON = "Common";

    private static final String CORRESPONDENT_TIMELINE_TYPE_BILL = "Bill";

    private static final String CORRESPONDENT_TIMELINE_TYPE_FEE = "Fee";

    private static final String CORRESPONDENT_TIMELINE_TYPE_DOC = "Doc";

    @Autowired
    private CPICorrespondentRepository cpiCorrespondentRepository;

    @Autowired
    private CorrespondentFeeRepository correspondentFeeRepository;

    @Autowired
    private CorrespondentBillRepository correspondentBillRepository;

    @Autowired
    private CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository;

    @Autowired
    private CorrespondentDocsRepository correspondentDocsRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    public CpiCorrespondentTimeLineService() {
    }

    public List<CpiCorrespondentTimeLineBean> getCpiCorrespondentTimeLineBeans(Long id) {
        List<CpiCorrespondentTimeLineBean> CpiCorrespondentTimeLineBeans = new ArrayList<>();
        // For Common
        StringBuilder incidenttemp = new StringBuilder();
        CPICorrespondent cpiCorrespondent = cpiCorrespondentRepository.findOne(id);
        incidenttemp.append("The Time of Case By ").append(userRepository.findUserByID(cpiCorrespondent.getHandlerUser()).getLogin());
        CpiCorrespondentTimeLineBean cpiCorrespondentTimeLineBean = new CpiCorrespondentTimeLineBean(
            cpiCorrespondent.getCaseDate(),
            incidenttemp.toString(),
            CORRESPONDENT_TIMELINE_TYPE_COMMON
        );
        CpiCorrespondentTimeLineBeans.add(cpiCorrespondentTimeLineBean);

        incidenttemp.setLength(0);
        incidenttemp.append("The Time of registed by ").append(userRepository.findUserByID(cpiCorrespondent.getHandlerUser()).getLogin());
        cpiCorrespondentTimeLineBean = new CpiCorrespondentTimeLineBean(
            cpiCorrespondent.getRegisterDate(),
            incidenttemp.toString(),
            CORRESPONDENT_TIMELINE_TYPE_COMMON
        );
        CpiCorrespondentTimeLineBeans.add(cpiCorrespondentTimeLineBean);


        // For Fees
        List<CorrespondentFee> correspondentFees = correspondentFeeRepository.findByCpiCorrespondentId(id);
        for (CorrespondentFee correspondentFee : correspondentFees) {
            StringBuilder incident = new StringBuilder();
            incident.append("Create ").append(correspondentFee.getCorrespondentFeeType().getCorrespondentFeeTypeName())
                .append(" ")
                .append(currencyRepository.findCurrencyByID(correspondentFee.getCurrency()).getNameAbbre())
                .append(correspondentFee.getCost())
                .append(" By ")
                .append(correspondentFee.getCreatedBy());

            cpiCorrespondentTimeLineBean = new CpiCorrespondentTimeLineBean(
                correspondentFee.getCostDate(),
                incident.toString(),
                CORRESPONDENT_TIMELINE_TYPE_FEE
            );
            CpiCorrespondentTimeLineBeans.add(cpiCorrespondentTimeLineBean);
        }

        // For bills
        List<CorrespondentBill> correspondentBills = correspondentBillRepository.findByCpiCorrespondentId(id);
        for (CorrespondentBill correspondentBill : correspondentBills) {
            StringBuilder incident = new StringBuilder();
            incident.append("Create ").append(correspondentBill.getBillFinanceType().getBillFinanceTypeName())
                .append(" Bill Code: ")
                .append(correspondentBill.getCorrespondentBillCode()).append(" ")
                .append(currencyRepository.findCurrencyByID(correspondentBill.getCurrency()).getNameAbbre())
                .append(correspondentBill.getAmount())
                .append(" By ")
                .append(correspondentBill.getCreatedBy());

            cpiCorrespondentTimeLineBean = new CpiCorrespondentTimeLineBean(
                correspondentBill.getCorrespondentBillDate(),
                incident.toString(),
                CORRESPONDENT_TIMELINE_TYPE_BILL
            );
            CpiCorrespondentTimeLineBeans.add(cpiCorrespondentTimeLineBean);

            List<CorrespondentBillStatusLog> correspondentBillStatusLogs =
                    correspondentBillStatusLogRepository.findByCorrespondentBillId(correspondentBill.getId());
            for (CorrespondentBillStatusLog correspondentBillStatusLog : correspondentBillStatusLogs) {
                StringBuilder incident1 = new StringBuilder();
                incident1
                    .append("Bill Code: ")
                    .append(correspondentBill.getCorrespondentBillCode())
                    .append(" update Status ")
                    .append(correspondentBillStatusLog.getBillStatusName())
                    .append(" By ")
                    .append(userRepository.findUserByID(correspondentBillStatusLog.getUpdateUser()).getLogin());

                cpiCorrespondentTimeLineBean = new CpiCorrespondentTimeLineBean(
                    correspondentBill.getCorrespondentBillDate(),
                    incident1.toString(),
                    CORRESPONDENT_TIMELINE_TYPE_BILL
                );
                CpiCorrespondentTimeLineBeans.add(cpiCorrespondentTimeLineBean);
            }
        }

        // For Doc
        List<CorrespondentDocs> correspondentDocs = correspondentDocsRepository.findByCpiCorrespondentId(id);
        for (CorrespondentDocs correspondentDoc : correspondentDocs) {
            StringBuilder incident2 = new StringBuilder();
            incident2.append("Upload Doc File : ").append(correspondentDoc.getDocumentName())
                .append(" By ")
                .append(correspondentDoc.getCreatedBy());

            cpiCorrespondentTimeLineBean = new CpiCorrespondentTimeLineBean(
                correspondentDoc.getUploadDate(),
                incident2.toString(),
                CORRESPONDENT_TIMELINE_TYPE_DOC
            );
            CpiCorrespondentTimeLineBeans.add(cpiCorrespondentTimeLineBean);
        }

        Collections.sort(CpiCorrespondentTimeLineBeans);

        return CpiCorrespondentTimeLineBeans;
    }


}
