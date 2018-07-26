/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CPICorrespondentBean
 * Author:   admin
 * Date:     2018/6/19 16:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.web.bean;

import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.repository.common.UserRepository;
import com.cpi.correspondent.service.CorrespondentBillQueryService;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.dto.common.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/19
 * @since 1.0.0
 */
@Component
public class CPICorrespondentBean {


    private UserRepository userRepository;


    private String correspondentCode;

    private String vesselName;

    private String clientRef;

    private String year;

    private String  portName;

    private String  correspondentTypeName;

    private String registDate;

    private String  caseDate;

    private String piClubName;

    private String piClubPersonName;

    private String handleUser;

    //for fees
    private Integer feeNumber;

    private BigDecimal feeAmount;

    //for bill
    private Integer billCreditNumber;

    private BigDecimal billCreditAmount;

    private Integer billDebitNumber;

    private BigDecimal billDebitAmount;

    public void init(CPICorrespondentDTO cpiCorrespondentDTO, UserRepository userRepository, CorrespondentFeeQueryService correspondentFeeQueryService,
                     CorrespondentBillQueryService correspondentBillQueryService) {
        this.userRepository = userRepository;
        this.correspondentCode = cpiCorrespondentDTO.getCorrespondentCode();
        this.vesselName = cpiCorrespondentDTO.getVesselName();
        this.clientRef = cpiCorrespondentDTO.getClientRef();
        this.year = cpiCorrespondentDTO.getYear();
        this.portName = "";
        this.correspondentTypeName = cpiCorrespondentDTO.getCorrespondentTypeCorrespondentTypeName();

        if (cpiCorrespondentDTO.getRegisterDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(cpiCorrespondentDTO.getRegisterDate(), ZoneId.systemDefault());
            this.registDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (cpiCorrespondentDTO.getCaseDate() != null) {
            this.caseDate   = LocalDateTime.ofInstant(cpiCorrespondentDTO.getCaseDate(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }


        this.piClubName = cpiCorrespondentDTO.getClubClubName();
        this.piClubPersonName = cpiCorrespondentDTO.getClubPersonClubPersonName();

        if (cpiCorrespondentDTO.getHandlerUser() != null) {
            UserDTO user = this.userRepository.findUserByID(cpiCorrespondentDTO.getHandlerUser());
            this.handleUser = user.getLogin();
        } else  {
            this.handleUser = " ";
        }

        List<CorrespondentFeeDTO> correspondentFeeDTOS = correspondentFeeQueryService.findByCpiCorrespondent(cpiCorrespondentDTO.getId());
        this.feeNumber = correspondentFeeDTOS.size();
        this.feeAmount = new BigDecimal(0);
        for (CorrespondentFeeDTO correspondentFeeDTO : correspondentFeeDTOS) {
            this.feeAmount = this.feeAmount.add(correspondentFeeDTO.getCostDollar());
        }

        this.billCreditNumber = 0;
        this.billDebitNumber = 0;
        this.billCreditAmount = new BigDecimal(0);
        this.billDebitAmount = new BigDecimal(0);
        List<CorrespondentBillDTO> correspondentBillDTOS = correspondentBillQueryService.findByCpiCorrespondent(cpiCorrespondentDTO.getId());
        for (CorrespondentBillDTO correspondentBillDTO : correspondentBillDTOS) {
            if (correspondentBillDTO.getExchangeAmount() != null) {
                if (correspondentBillDTO.getBillFinanceTypeId().equals(BillFinanceType.BILL_FINANCE_TYPE_CREDIT)) {
                    this.billCreditNumber++;
                    this.billCreditAmount = this.billCreditAmount.add(correspondentBillDTO.getExchangeAmount());
                } else  if (correspondentBillDTO.getBillFinanceTypeId().equals(BillFinanceType.BILL_FINANCE_TYPE_DEBIT)) {
                    this.billDebitNumber++;
                    this.billDebitAmount = this.billDebitAmount.add(correspondentBillDTO.getExchangeAmount());
                }
            }

        }

    }

    @Override
    public String toString() {
        return "CPICorrespondentBean{" +
            "userRepository=" + userRepository +
            ", correspondentCode='" + correspondentCode + '\'' +
            ", vesselName='" + vesselName + '\'' +
            ", clientRef='" + clientRef + '\'' +
            ", year='" + year + '\'' +
            ", portName='" + portName + '\'' +
            ", correspondentTypeName='" + correspondentTypeName + '\'' +
            ", registDate=" + registDate +
            ", caseDate=" + caseDate +
            ", piClubName='" + piClubName + '\'' +
            ", piClubPersonName='" + piClubPersonName + '\'' +
            ", handleUser='" + handleUser + '\'' +
            ", feeNumber=" + feeNumber +
            ", feeAmount=" + feeAmount +
            ", billCreditNumber=" + billCreditNumber +
            ", billCreditAmount=" + billCreditAmount +
            ", billDebitNumber=" + billDebitNumber +
            ", billDebitAmount=" + billDebitAmount +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CPICorrespondentBean)) return false;
        CPICorrespondentBean that = (CPICorrespondentBean) o;
        return Objects.equals(getUserRepository(), that.getUserRepository()) &&
            Objects.equals(getCorrespondentCode(), that.getCorrespondentCode()) &&
            Objects.equals(getVesselName(), that.getVesselName()) &&
            Objects.equals(getClientRef(), that.getClientRef()) &&
            Objects.equals(getYear(), that.getYear()) &&
            Objects.equals(getPortName(), that.getPortName()) &&
            Objects.equals(getCorrespondentTypeName(), that.getCorrespondentTypeName()) &&
            Objects.equals(getRegistDate(), that.getRegistDate()) &&
            Objects.equals(getCaseDate(), that.getCaseDate()) &&
            Objects.equals(getPiClubName(), that.getPiClubName()) &&
            Objects.equals(getPiClubPersonName(), that.getPiClubPersonName()) &&
            Objects.equals(getHandleUser(), that.getHandleUser()) &&
            Objects.equals(getFeeNumber(), that.getFeeNumber()) &&
            Objects.equals(getFeeAmount(), that.getFeeAmount()) &&
            Objects.equals(getBillCreditNumber(), that.getBillCreditNumber()) &&
            Objects.equals(getBillCreditAmount(), that.getBillCreditAmount()) &&
            Objects.equals(getBillDebitNumber(), that.getBillDebitNumber()) &&
            Objects.equals(getBillDebitAmount(), that.getBillDebitAmount());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserRepository(), getCorrespondentCode(), getVesselName(), getClientRef(), getYear(), getPortName(), getCorrespondentTypeName(), getRegistDate(), getCaseDate(), getPiClubName(), getPiClubPersonName(), getHandleUser(), getFeeNumber(), getFeeAmount(), getBillCreditNumber(), getBillCreditAmount(), getBillDebitNumber(), getBillDebitAmount());
    }

    public Integer getBillCreditNumber() {
        return billCreditNumber;
    }

    public void setBillCreditNumber(Integer billCreditNumber) {
        this.billCreditNumber = billCreditNumber;
    }

    public BigDecimal getBillCreditAmount() {
        return billCreditAmount;
    }

    public void setBillCreditAmount(BigDecimal billCreditAmount) {
        this.billCreditAmount = billCreditAmount;
    }

    public Integer getBillDebitNumber() {
        return billDebitNumber;
    }

    public void setBillDebitNumber(Integer billDebitNumber) {
        this.billDebitNumber = billDebitNumber;
    }

    public BigDecimal getBillDebitAmount() {
        return billDebitAmount;
    }

    public void setBillDebitAmount(BigDecimal billDebitAmount) {
        this.billDebitAmount = billDebitAmount;
    }

    public Integer getFeeNumber() {
        return feeNumber;
    }

    public void setFeeNumber(Integer feeNumber) {
        this.feeNumber = feeNumber;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }



    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getCorrespondentCode() {
        return correspondentCode;
    }

    public void setCorrespondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getCorrespondentTypeName() {
        return correspondentTypeName;
    }

    public void setCorrespondentTypeName(String correspondentTypeName) {
        this.correspondentTypeName = correspondentTypeName;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
    }

    public String getPiClubName() {
        return piClubName;
    }

    public void setPiClubName(String piClubName) {
        this.piClubName = piClubName;
    }

    public String getPiClubPersonName() {
        return piClubPersonName;
    }

    public void setPiClubPersonName(String piClubPersonName) {
        this.piClubPersonName = piClubPersonName;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }
}
