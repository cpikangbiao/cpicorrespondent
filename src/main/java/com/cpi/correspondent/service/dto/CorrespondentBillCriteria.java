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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cpi.correspondent.domain.CorrespondentBill} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CorrespondentBillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /correspondent-bills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentBillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numberId;

    private StringFilter year;

    private StringFilter correspondentBillCode;

    private InstantFilter correspondentBillDate;

    private StringFilter receiver;

    private StringFilter mainContent;

    private InstantFilter dueDate;

    private BigDecimalFilter amount;

    private LongFilter currency;

    private FloatFilter currencyRate;

    private InstantFilter exchangeDate;

    private LongFilter exchangeCurrency;

    private FloatFilter exchangeRate;

    private BigDecimalFilter exchangeAmount;

    private LongFilter correspondentBillStatusId;

    private LongFilter creditId;

    private LongFilter cpiCorrespondentId;

    private LongFilter billFinanceTypeId;

    public CorrespondentBillCriteria(){
    }

    public CorrespondentBillCriteria(CorrespondentBillCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.numberId = other.numberId == null ? null : other.numberId.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.correspondentBillCode = other.correspondentBillCode == null ? null : other.correspondentBillCode.copy();
        this.correspondentBillDate = other.correspondentBillDate == null ? null : other.correspondentBillDate.copy();
        this.receiver = other.receiver == null ? null : other.receiver.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.currencyRate = other.currencyRate == null ? null : other.currencyRate.copy();
        this.exchangeDate = other.exchangeDate == null ? null : other.exchangeDate.copy();
        this.exchangeCurrency = other.exchangeCurrency == null ? null : other.exchangeCurrency.copy();
        this.exchangeRate = other.exchangeRate == null ? null : other.exchangeRate.copy();
        this.exchangeAmount = other.exchangeAmount == null ? null : other.exchangeAmount.copy();
        this.cpiCorrespondentId = other.cpiCorrespondentId == null ? null : other.cpiCorrespondentId.copy();
        this.billFinanceTypeId = other.billFinanceTypeId == null ? null : other.billFinanceTypeId.copy();
    }

    @Override
    public CorrespondentBillCriteria copy() {
        return new CorrespondentBillCriteria(this);
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

    public StringFilter getMainContent() {
        return mainContent;
    }

    public void setMainContent(StringFilter mainContent) {
        this.mainContent = mainContent;
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

    public LongFilter getCorrespondentBillStatusId() {
        return correspondentBillStatusId;
    }

    public void setCorrespondentBillStatusId(LongFilter correspondentBillStatusId) {
        this.correspondentBillStatusId = correspondentBillStatusId;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrespondentBillCriteria that = (CorrespondentBillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numberId, that.numberId) &&
            Objects.equals(year, that.year) &&
            Objects.equals(correspondentBillCode, that.correspondentBillCode) &&
            Objects.equals(correspondentBillDate, that.correspondentBillDate) &&
            Objects.equals(receiver, that.receiver) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(currencyRate, that.currencyRate) &&
            Objects.equals(exchangeDate, that.exchangeDate) &&
            Objects.equals(exchangeCurrency, that.exchangeCurrency) &&
            Objects.equals(exchangeRate, that.exchangeRate) &&
            Objects.equals(exchangeAmount, that.exchangeAmount) &&
            Objects.equals(cpiCorrespondentId, that.cpiCorrespondentId) &&
            Objects.equals(billFinanceTypeId, that.billFinanceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numberId,
        year,
        correspondentBillCode,
        correspondentBillDate,
        receiver,
        dueDate,
        amount,
        currency,
        currencyRate,
        exchangeDate,
        exchangeCurrency,
        exchangeRate,
        exchangeAmount,
        cpiCorrespondentId,
        billFinanceTypeId
        );
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
                (mainContent != null ? "mainContent=" + mainContent + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (currencyRate != null ? "currencyRate=" + currencyRate + ", " : "") +
                (exchangeDate != null ? "exchangeDate=" + exchangeDate + ", " : "") +
                (exchangeCurrency != null ? "exchangeCurrency=" + exchangeCurrency + ", " : "") +
                (exchangeRate != null ? "exchangeRate=" + exchangeRate + ", " : "") +
                (exchangeAmount != null ? "exchangeAmount=" + exchangeAmount + ", " : "") +
                (correspondentBillStatusId != null ? "correspondentBillStatusId=" + correspondentBillStatusId + ", " : "") +
                (creditId != null ? "creditId=" + creditId + ", " : "") +
                (cpiCorrespondentId != null ? "cpiCorrespondentId=" + cpiCorrespondentId + ", " : "") +
                (billFinanceTypeId != null ? "billFinanceTypeId=" + billFinanceTypeId + ", " : "") +
            "}";
    }

}
