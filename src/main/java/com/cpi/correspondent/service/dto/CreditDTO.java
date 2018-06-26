package com.cpi.correspondent.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Credit entity.
 */
public class CreditDTO implements Serializable {

    private Long id;

    private Integer numberId;

    private String creditorName;

    private String creditorAddress;

    private String bankName;

    private String bankAddress;

    private String accountNo;

    private String corrBankName;

    private String corrBankAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(String creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCorrBankName() {
        return corrBankName;
    }

    public void setCorrBankName(String corrBankName) {
        this.corrBankName = corrBankName;
    }

    public String getCorrBankAddress() {
        return corrBankAddress;
    }

    public void setCorrBankAddress(String corrBankAddress) {
        this.corrBankAddress = corrBankAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditDTO creditDTO = (CreditDTO) o;
        if(creditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditDTO{" +
            "id=" + getId() +
            ", numberId=" + getNumberId() +
            ", creditorName='" + getCreditorName() + "'" +
            ", creditorAddress='" + getCreditorAddress() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankAddress='" + getBankAddress() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", corrBankName='" + getCorrBankName() + "'" +
            ", corrBankAddress='" + getCorrBankAddress() + "'" +
            "}";
    }
}
