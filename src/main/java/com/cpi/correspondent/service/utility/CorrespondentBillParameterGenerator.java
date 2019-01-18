/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CorrespondentBillParameterUtility
 * Author:   admin
 * Date:     2019/1/18 13:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.service.utility;

import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.repository.common.CurrencyRepository;
import com.cpi.correspondent.service.dto.common.CurrencyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2019/1/18
 * @since 1.0.0
 */
@Service
@Transactional
public class CorrespondentBillParameterGenerator {

    private static final int CORRESPONDENT_FEE_TYPE_CORRESPONDENT = 5;

    private static final int CORRESPONDENT_FEE_TYPE_OTHER = 4;

    private static final int CORRESPONDENT_FEE_TYPE_SURVEYOR = 1;

    private static final int CORRESPONDENT_FEE_TYPE_LAWAY = 2;

    private static final int CORRESPONDENT_FEE_TYPE_EXPERT = 3;

    private static final int CORRESPONDENT_FEE_TYPE_BAIL = 6;


    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    public Map<String, Object> createCreditBillMap(CorrespondentBill correspondentbill) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("receiver", correspondentbill.getReceiver());
        map.put("DebitNoteCode", correspondentbill.getCorrespondentBillCode());

        if (correspondentbill.getCorrespondentBillDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(correspondentbill.getCorrespondentBillDate(), ZoneId.systemDefault());
            map.put("DebitDate", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (correspondentbill.getCpiCorrespondent() != null) {
            map.put("cpiRef", correspondentbill.getCpiCorrespondent().getCorrespondentCode());
//            map.put("clientRef", correspondentbill.getCpiCorrespondent().getClientRef());
        }
        if (correspondentbill.getCredit() != null) {
            map.put("bankName", correspondentbill.getCredit().getBankName());
            map.put("bankAccount", correspondentbill.getCredit().getAccountNo());
        }


        if (correspondentbill.getExchangeCurrency() != null) {
            CurrencyDTO currencyDTO = currencyRepository.findCurrencyByID(correspondentbill.getExchangeCurrency());

            DecimalFormat decimalFormat = new DecimalFormat(",###,##0.00");
            StringBuilder exchangeeAmount = new StringBuilder();
            exchangeeAmount.append(currencyDTO.getNameAbbre())
                .append(decimalFormat.format(correspondentbill.getExchangeAmount()));
            map.put("sumAmount", exchangeeAmount.toString());

            if (correspondentbill.getRemark() != null) {
                exchangeeAmount.append(" (")
                    .append(correspondentbill.getRemark())
                    .append(")");
            }

            map.put("surveyAmount", exchangeeAmount.toString());

        }

        if (correspondentbill.getMainContent() != null) {
            map.put("mv", correspondentbill.getMainContent());
        }

        return map;
    }

    public Map<String, Object> createDebitBillMap(CorrespondentBill correspondentbill) {
        Map<String, Object> parameter = new HashMap<String, Object>();

        parameter.put("reciever", correspondentbill.getReceiver());
        parameter.put("DebitNoteCode", correspondentbill.getCorrespondentBillCode());

        if (correspondentbill.getCorrespondentBillDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(correspondentbill.getCorrespondentBillDate(), ZoneId.systemDefault());
            parameter.put("DebitDate", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        //1.4.9 	2018-08-20 	调整Debit账单显示数据内容和格式。 	新需求开发 	防损部 	程彦民
        if (correspondentbill.getCpiCorrespondent().getClub() != null) {
            if (correspondentbill.getCpiCorrespondent().getClub().getRemark() != null) {
                parameter.put("co", correspondentbill.getCpiCorrespondent().getClub().getRemark());
            } else {
                parameter.put("co", correspondentbill.getCpiCorrespondent().getClub().getClubName());
            }
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

        if (correspondentbill.getMainContent() != null) {
            parameter.put("mv", correspondentbill.getMainContent());
        }

        DecimalFormat decimalFormat = new DecimalFormat(",###,##0.00");
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
