package com.cpi.correspondent.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the CorrespondentBill entity. This class is used in CorrespondentBillResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-bills?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentBillCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter numberId;

    private StringFilter year;

    private StringFilter correspondentBillCode;

    private InstantFilter correspondentBillDate;

    private StringFilter receiver;

    private InstantFilter dueDate;

    private BigDecimalFilter amount;

    private LongFilter currency;

    private FloatFilter currencyRate;

    private InstantFilter exchangeDate;

    private LongFilter exchangeCurrency;

    private FloatFilter exchangeRate;

    private BigDecimalFilter exchangeAmount;

    private LongFilter creditId;

    private LongFilter cpiCorrespondentId;

    private LongFilter billFinanceTypeId;

    public CorrespondentBillCriteria() {
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

    public StringFilter getYear() {
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getCorrespondentBillCode() {
        return correspondentBillCode;
    }

    public void setCorrespondentBillCode(StringFilter correspondentBillCode) {
        this.correspondentBillCode = correspondentBillCode;
    }

    public InstantFilter getCorrespondentBillDate() {
        return correspondentBillDate;
    }

    public void setCorrespondentBillDate(InstantFilter correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
    }

    public StringFilter getReceiver() {
        return receiver;
    }

    public void setReceiver(StringFilter receiver) {
        this.receiver = receiver;
    }

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public LongFilter getCurrency() {
        return currency;
    }

    public void setCurrency(LongFilter currency) {
        this.currency = currency;
    }

    public FloatFilter getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(FloatFilter currencyRate) {
        this.currencyRate = currencyRate;
    }

    public InstantFilter getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(InstantFilter exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public LongFilter getExchangeCurrency() {
        return exchangeCurrency;
    }

    public void setExchangeCurrency(LongFilter exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public FloatFilter getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(FloatFilter exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimalFilter getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(BigDecimalFilter exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public LongFilter getCreditId() {
        return creditId;
    }

    public void setCreditId(LongFilter creditId) {
        this.creditId = creditId;
    }

    public LongFilter getCpiCorrespondentId() {
        return cpiCorrespondentId;
    }

    public void setCpiCorrespondentId(LongFilter cpiCorrespondentId) {
        this.cpiCorrespondentId = cpiCorrespondentId;
    }

    public LongFilter getBillFinanceTypeId() {
        return billFinanceTypeId;
    }

    public void setBillFinanceTypeId(LongFilter billFinanceTypeId) {
        this.billFinanceTypeId = billFinanceTypeId;
    }

    @Override
    public String toString() {
        return "CorrespondentBillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numberId != null ? "numberId=" + numberId + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (correspondentBillCode != null ? "correspondentBillCode=" + correspondentBillCode + ", " : "") +
                (correspondentBillDate != null ? "correspondentBillDate=" + correspondentBillDate + ", " : "") +
                (receiver != null ? "receiver=" + receiver + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (currencyRate != null ? "currencyRate=" + currencyRate + ", " : "") +
                (exchangeDate != null ? "exchangeDate=" + exchangeDate + ", " : "") +
                (exchangeCurrency != null ? "exchangeCurrency=" + exchangeCurrency + ", " : "") +
                (exchangeRate != null ? "exchangeRate=" + exchangeRate + ", " : "") +
                (exchangeAmount != null ? "exchangeAmount=" + exchangeAmount + ", " : "") +
                (creditId != null ? "creditId=" + creditId + ", " : "") +
                (cpiCorrespondentId != null ? "cpiCorrespondentId=" + cpiCorrespondentId + ", " : "") +
                (billFinanceTypeId != null ? "billFinanceTypeId=" + billFinanceTypeId + ", " : "") +
            "}";
    }

}
