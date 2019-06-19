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
import com.cpi.correspondent.service.utility.CorrespondentBillCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/19
 * @since 1.0.0
 */

@Service
public class CorrespondentBillCreateService {

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

    public CorrespondentBillDTO createCorrespondentBill(List<Long> feeIDs, Long billFinanceTypeId) {
        CorrespondentBillDTO correspondentBillDTO = null;
        List<CorrespondentFeeDTO> correspondentFees = new ArrayList();
        HashSet<Long> currencySet = new HashSet();
        Long cpiCorrespondentId = null;
        for (Long feeID : feeIDs) {
            Optional<CorrespondentFeeDTO> correspondentFeeDTO = correspondentFeeService.findOne(feeID);
            currencySet.add(correspondentFeeDTO.get().getCurrency());
            correspondentFees.add(correspondentFeeDTO.get());
            cpiCorrespondentId = correspondentFeeDTO.get().getCpiCorrespondentId();
        }

        for (Long currency : currencySet) {
            List<CorrespondentFeeDTO> newCorrespondentFees = new ArrayList();
            for (CorrespondentFeeDTO correspondentFee : correspondentFees) {
                if (correspondentFee.getCurrency().equals(currency)) {
                    newCorrespondentFees.add(correspondentFee);
                }
            }

            correspondentBillDTO = createCorrespondentBill(newCorrespondentFees, billFinanceTypeId, cpiCorrespondentId, currency);
        }

        return correspondentBillDTO;
    }

    private CorrespondentBillDTO createCorrespondentBill(List<CorrespondentFeeDTO> correspondentFeeDTOs, Long billFinanceTypeId, Long cpiCorrespondentId, Long currency) {
        CorrespondentBill correspondentBill = new CorrespondentBill();

        Optional<CPICorrespondent> cpiCorrespondent = cpiCorrespondentRepository.findById(cpiCorrespondentId);
        correspondentBill.setCpiCorrespondent(cpiCorrespondent.get());

        Optional<BillFinanceType> billFinanceType = billFinanceTypeRepository.findById(billFinanceTypeId);
        correspondentBill.setBillFinanceType(billFinanceType.get());
        correspondentBill.setAmount(new BigDecimal(0.0));

        Optional<CorrespondentBillStatus> correspondentBillStatus = correspondentBillStatusRepository.findById(CorrespondentBillStatus.CORRESPONDENT_BILL_STATUS_NOPAID);
        correspondentBill.setCorrespondentBillStatus(correspondentBillStatus.get());

        correspondentBill.setCorrespondentBillDate(Instant.now());
        correspondentBill.setCurrency(currency);
        correspondentBill.setCurrencyRate(Float.valueOf(1));

        correspondentBill.setExchangeAmount(new BigDecimal(0.0));
        correspondentBill.setExchangeCurrency(currency);
        correspondentBill.setExchangeDate(Instant.now());
        correspondentBill.setExchangeRate(Float.valueOf(1));

        //1.4.8 	2018-08-06 	新加账单时，Due Date默认为当前时间的后一个月为默认时间。 	新需求开发 	防损部 	韦毓良
        LocalDateTime localDateTime = (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusMonths(1);
        correspondentBill.setDueDate(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        correspondentBill.setYear(cpiCorrespondent.get().getYear());
        CorrespondentBill maxBill  = correspondentBillRepository.findTopByYearAndBillFinanceTypeIdOrderByNumberIdDesc(correspondentBill.getYear(), billFinanceTypeId);
        Integer maxNumber = null;
        if (maxBill != null) {
            maxNumber = maxBill.getNumberId();
        }

        if (maxNumber == null) {
            maxNumber = 0;
        }
        correspondentBill.setNumberId(maxNumber + 1);
        correspondentBill.setCorrespondentBillCode(
            CorrespondentBillCodeGenerator.createBillCode(correspondentBill.getYear(), correspondentBill.getNumberId(), billFinanceTypeId));

        correspondentBill.setReceiver(null);
        correspondentBill.setRemark(null);

        correspondentBillRepository.save(correspondentBill);

        BigDecimal amount = new BigDecimal(0.0);
        for (CorrespondentFeeDTO correspondentFeeDTO : correspondentFeeDTOs) {
            createCorrespondentFeeAndBill(correspondentFeeDTO, correspondentBill, billFinanceTypeId);
            amount = amount.add(correspondentFeeDTO.getCost());
        }
        correspondentBill.setAmount(amount);
        correspondentBill.setExchangeAmount(amount);

        correspondentBillRepository.save(correspondentBill);

        return  correspondentBillMapper.toDto(correspondentBill);
    }

    private CorrespondentFeeAndBill createCorrespondentFeeAndBill(CorrespondentFeeDTO correspondentFeeDTO, CorrespondentBill correspondentBill, Long billFinanceType) {
        CorrespondentFeeAndBill correspondentFeeAndBill = new CorrespondentFeeAndBill();

        if (billFinanceType.equals(BillFinanceType.BILL_FINANCE_TYPE_CREDIT)) {
            correspondentFeeAndBill.setCorrespondentCreditBill(correspondentBill);
        } else {
            correspondentFeeAndBill.setCorrespondentDebitBill(correspondentBill);
        }

        Optional<CorrespondentFee> correspondentFee = correspondentFeeRepository.findById(correspondentFeeDTO.getId());
        correspondentFeeAndBill.setCorrespondentFee(correspondentFee.get());

        correspondentFeeAndBillRepository.save(correspondentFeeAndBill);

        return correspondentFeeAndBill;
    }



}
