/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CreateBillService
 * Author:   admin
 * Date:     2018/6/19 11:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.service;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.repository.*;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/19
 * @since 1.0.0
 */

@Service
public class CreateBillService {

    @Autowired
    private CorrespondentFeeService correspondentFeeService;

    @Autowired
    private CorrespondentBillRepository correspondentBillRepository;

    @Autowired
    private CPICorrespondentRepository cpiCorrespondentRepository;

    @Autowired
    private CorrespondentFeeRepository correspondentFeeRepository;

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    @Autowired
    private BillFinanceTypeRepository billFinanceTypeRepository;

    @Autowired
    private CorrespondentBillMapper correspondentBillMapper;

    @Autowired
    private CorrespondentBillStatusRepository correspondentBillStatusRepository;

    public CorrespondentBillDTO CreateCorrespondentBill(List<Long> feeIDs, Long billFinanceTypeId) {
        CorrespondentBillDTO correspondentBillDTO = null;
        List<CorrespondentFeeDTO> correspondentFees = new ArrayList();
        HashSet<Long> currencySet = new HashSet();
        Long cpiCorrespondentId = null;
        for (Long feeID : feeIDs) {
            CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeService.findOne(feeID);
            currencySet.add(correspondentFeeDTO.getCurrency());
            correspondentFees.add(correspondentFeeDTO);
            cpiCorrespondentId = correspondentFeeDTO.getCpiCorrespondentId();
        }

        for (Long currency : currencySet) {
            List<CorrespondentFeeDTO> newCorrespondentFees = new ArrayList();
            for (CorrespondentFeeDTO correspondentFee : correspondentFees) {
                if (correspondentFee.getCurrency().equals(currency)) {
                    newCorrespondentFees.add(correspondentFee);
                }
            }

            correspondentBillDTO = CreateBill(newCorrespondentFees, billFinanceTypeId, cpiCorrespondentId, currency);
        }

        return correspondentBillDTO;
    }

    private CorrespondentBillDTO CreateBill(List<CorrespondentFeeDTO> correspondentFeeDTOs, Long billFinanceTypeId, Long cpiCorrespondentId, Long currency) {
        CorrespondentBill correspondentBill = new CorrespondentBill();

        CPICorrespondent cpiCorrespondent = cpiCorrespondentRepository.findOne(cpiCorrespondentId);
        correspondentBill.setCpiCorrespondent(cpiCorrespondent);

        BillFinanceType billFinanceType = billFinanceTypeRepository.findOne(billFinanceTypeId);
        correspondentBill.setBillFinanceType(billFinanceType);
        correspondentBill.setAmount(new BigDecimal(0.0));

        CorrespondentBillStatus correspondentBillStatus = correspondentBillStatusRepository.findOne(CorrespondentBillStatus.CORRESPONDENT_BILL_STATUS_NOPAID);
        correspondentBill.setCorrespondentBillStatus(correspondentBillStatus);

        correspondentBill.setCorrespondentBillDate(Instant.now());
        correspondentBill.setCurrency(currency);
        correspondentBill.setCurrencyRate(new Float(1.0));
        correspondentBill.setDueDate(Instant.now());
        correspondentBill.setExchangeAmount(new BigDecimal(0.0));
        correspondentBill.setExchangeCurrency(currency);
        correspondentBill.setExchangeDate(Instant.now());
        correspondentBill.setExchangeRate(new Float(1.0));

        correspondentBill.setYear(Year.now().toString());
        CorrespondentBill maxBill  = correspondentBillRepository.findTopByYearOrderByNumberIdDesc(correspondentBill.getYear());
        Integer maxNumber = null;
        if (maxBill != null) {
            maxNumber = maxBill.getNumberId();
        }

        if (maxNumber == null) {
            maxNumber = 0;
        }
        correspondentBill.setNumberId(maxNumber + 1);
        correspondentBill.setCorrespondentBillCode(CreateBillCode(correspondentBill.getYear(), correspondentBill.getNumberId()));

        correspondentBill.setReceiver(null);
        correspondentBill.setRemark(null);

        correspondentBillRepository.save(correspondentBill);

        BigDecimal amount = new BigDecimal(0.0);
        for (CorrespondentFeeDTO correspondentFeeDTO : correspondentFeeDTOs) {
            CreateCorrespondentFeeAndBill(correspondentFeeDTO, correspondentBill, billFinanceTypeId);
            amount = amount.add(correspondentFeeDTO.getCost());
        }
        correspondentBill.setAmount(amount);
        correspondentBill.setExchangeAmount(amount);

        correspondentBillRepository.save(correspondentBill);

        return  correspondentBillMapper.toDto(correspondentBill);
    }

    private CorrespondentFeeAndBill CreateCorrespondentFeeAndBill(CorrespondentFeeDTO correspondentFeeDTO, CorrespondentBill correspondentBill, Long billFinanceType) {
        CorrespondentFeeAndBill correspondentFeeAndBill = new CorrespondentFeeAndBill();

        if (billFinanceType.equals(BillFinanceType.BILL_FINANCE_TYPE_CREDIT)) {
            correspondentFeeAndBill.setCorrespondentCreditBill(correspondentBill);
        } else {
            correspondentFeeAndBill.setCorrespondentDebitBill(correspondentBill);
        }

        CorrespondentFee correspondentFee = correspondentFeeRepository.findOne(correspondentFeeDTO.getId());
        correspondentFeeAndBill.setCorrespondentFee(correspondentFee);

        correspondentFeeAndBillRepository.save(correspondentFeeAndBill);

        return correspondentFeeAndBill;
    }

    private String CreateBillCode(String year, Integer numberId) {
        StringBuilder billCode = new StringBuilder();
        NumberFormat formatter3 = new DecimalFormat("000");
        billCode.append("C").append(year).append(formatter3.format(numberId));

        return billCode.toString();
    }

}
