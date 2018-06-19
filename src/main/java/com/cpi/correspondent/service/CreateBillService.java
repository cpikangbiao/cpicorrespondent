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

import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

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

    public void CreateCreditBill(List<Long> feeIDs) {
        List<CorrespondentFeeDTO> correspondentFees = new ArrayList();
        HashSet<Long> currencySet = new HashSet();
        for (Long feeID : feeIDs) {
            CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeService.findOne(feeID);
            currencySet.add(correspondentFeeDTO.getCurrency());
            correspondentFees.add(correspondentFeeDTO);
        }

        for (Long currency : currencySet) {
            List<CorrespondentFeeDTO> newCorrespondentFees = new ArrayList();
            for (CorrespondentFeeDTO correspondentFee : correspondentFees) {
                if (correspondentFee.getCurrency().equals(currency)) {
                    newCorrespondentFees.add(correspondentFee);
                }


            }
        }


    }

    public void CreateDebitBill(List<Long> feeIDs) {

    }

    private void CreateBill() {

    }

}
