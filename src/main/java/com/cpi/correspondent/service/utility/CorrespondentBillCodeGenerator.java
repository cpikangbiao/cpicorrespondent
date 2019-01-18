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

import com.cpi.correspondent.domain.BillFinanceType;
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
import java.text.NumberFormat;
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

public class CorrespondentBillCodeGenerator {

     public static String createBillCode(String year, Integer numberId, Long billFinanceTypeId) {
        StringBuilder billCode = new StringBuilder();
        NumberFormat formatter3 = new DecimalFormat("000");

        if (billFinanceTypeId.equals(BillFinanceType.BILL_FINANCE_TYPE_CREDIT)) {
            billCode.append("C");
        } else {
            billCode.append("D");
        }
        billCode.append(year).append(formatter3.format(numberId));

        return billCode.toString();
    }
}
