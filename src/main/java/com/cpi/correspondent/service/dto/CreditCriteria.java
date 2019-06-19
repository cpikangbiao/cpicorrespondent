package com.cpi.correspondent.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.cpi.correspondent.domain.Credit} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CreditResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /credits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CreditCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numberId;

    private StringFilter creditorName;

    private StringFilter creditorAddress;

    private StringFilter bankName;

    private StringFilter bankAddress;

    private StringFilter accountNo;

    private StringFilter corrBankName;

    private StringFilter corrBankAddress;

    public CreditCriteria(){
    }

    public CreditCriteria(CreditCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.numberId = other.numberId == null ? null : other.numberId.copy();
        this.creditorName = other.creditorName == null ? null : other.creditorName.copy();
        this.creditorAddress = other.creditorAddress == null ? null : other.creditorAddress.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.bankAddress = other.bankAddress == null ? null : other.bankAddress.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.corrBankName = other.corrBankName == null ? null : other.corrBankName.copy();
        this.corrBankAddress = other.corrBankAddress == null ? null : other.corrBankAddress.copy();
    }

    @Override
    public CreditCriteria copy() {
        return new CreditCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumberId() {
        return numberId;
    }

    public void setNumberId(IntegerFilter numberId) {
        this.numberId = numberId;
    }

    public StringFilter getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(StringFilter creditorName) {
        this.creditorName = creditorName;
    }

    public StringFilter getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(StringFilter creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(StringFilter bankAddress) {
        this.bankAddress = bankAddress;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getCorrBankName() {
        return corrBankName;
    }

    public void setCorrBankName(StringFilter corrBankName) {
        this.corrBankName = corrBankName;
    }

    public StringFilter getCorrBankAddress() {
        return corrBankAddress;
    }

    public void setCorrBankAddress(StringFilter corrBankAddress) {
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
        final CreditCriteria that = (CreditCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numberId, that.numberId) &&
            Objects.equals(creditorName, that.creditorName) &&
            Objects.equals(creditorAddress, that.creditorAddress) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(bankAddress, that.bankAddress) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(corrBankName, that.corrBankName) &&
            Objects.equals(corrBankAddress, that.corrBankAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numberId,
        creditorName,
        creditorAddress,
        bankName,
        bankAddress,
        accountNo,
        corrBankName,
        corrBankAddress
        );
    }

    @Override
    public String toString() {
        return "CreditCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numberId != null ? "numberId=" + numberId + ", " : "") +
                (creditorName != null ? "creditorName=" + creditorName + ", " : "") +
                (creditorAddress != null ? "creditorAddress=" + creditorAddress + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (bankAddress != null ? "bankAddress=" + bankAddress + ", " : "") +
                (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
                (corrBankName != null ? "corrBankName=" + corrBankName + ", " : "") +
                (corrBankAddress != null ? "corrBankAddress=" + corrBankAddress + ", " : "") +
            "}";
    }

}
