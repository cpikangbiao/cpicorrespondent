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
 * Criteria class for the {@link com.cpi.correspondent.domain.CorrespondentFee} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CorrespondentFeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /correspondent-fees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentFeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clientNo;

    private IntegerFilter numberId;

    private LongFilter currency;

    private FloatFilter currencyRate;

    private BigDecimalFilter cost;

    private InstantFilter costDate;

    private BigDecimalFilter costDollar;

    private LongFilter correspondentFeeTypeId;

    private LongFilter cpiCorrespondentId;

    public CorrespondentFeeCriteria(){
    }

    public CorrespondentFeeCriteria(CorrespondentFeeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.clientNo = other.clientNo == null ? null : other.clientNo.copy();
        this.numberId = other.numberId == null ? null : other.numberId.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.currencyRate = other.currencyRate == null ? null : other.currencyRate.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.costDate = other.costDate == null ? null : other.costDate.copy();
        this.costDollar = other.costDollar == null ? null : other.costDollar.copy();
        this.correspondentFeeTypeId = other.correspondentFeeTypeId == null ? null : other.correspondentFeeTypeId.copy();
        this.cpiCorrespondentId = other.cpiCorrespondentId == null ? null : other.cpiCorrespondentId.copy();
    }

    @Override
    public CorrespondentFeeCriteria copy() {
        return new CorrespondentFeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClientNo() {
        return clientNo;
    }

    public void setClientNo(StringFilter clientNo) {
        this.clientNo = clientNo;
    }

    public IntegerFilter getNumberId() {
        return numberId;
    }

    public void setNumberId(IntegerFilter numberId) {
        this.numberId = numberId;
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

    public BigDecimalFilter getCost() {
        return cost;
    }

    public void setCost(BigDecimalFilter cost) {
        this.cost = cost;
    }

    public InstantFilter getCostDate() {
        return costDate;
    }

    public void setCostDate(InstantFilter costDate) {
        this.costDate = costDate;
    }

    public BigDecimalFilter getCostDollar() {
        return costDollar;
    }

    public void setCostDollar(BigDecimalFilter costDollar) {
        this.costDollar = costDollar;
    }

    public LongFilter getCorrespondentFeeTypeId() {
        return correspondentFeeTypeId;
    }

    public void setCorrespondentFeeTypeId(LongFilter correspondentFeeTypeId) {
        this.correspondentFeeTypeId = correspondentFeeTypeId;
    }

    public LongFilter getCpiCorrespondentId() {
        return cpiCorrespondentId;
    }

    public void setCpiCorrespondentId(LongFilter cpiCorrespondentId) {
        this.cpiCorrespondentId = cpiCorrespondentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrespondentFeeCriteria that = (CorrespondentFeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clientNo, that.clientNo) &&
            Objects.equals(numberId, that.numberId) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(currencyRate, that.currencyRate) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(costDate, that.costDate) &&
            Objects.equals(costDollar, that.costDollar) &&
            Objects.equals(correspondentFeeTypeId, that.correspondentFeeTypeId) &&
            Objects.equals(cpiCorrespondentId, that.cpiCorrespondentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clientNo,
        numberId,
        currency,
        currencyRate,
        cost,
        costDate,
        costDollar,
        correspondentFeeTypeId,
        cpiCorrespondentId
        );
    }

    @Override
    public String toString() {
        return "CorrespondentFeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clientNo != null ? "clientNo=" + clientNo + ", " : "") +
                (numberId != null ? "numberId=" + numberId + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (currencyRate != null ? "currencyRate=" + currencyRate + ", " : "") +
                (cost != null ? "cost=" + cost + ", " : "") +
                (costDate != null ? "costDate=" + costDate + ", " : "") +
                (costDollar != null ? "costDollar=" + costDollar + ", " : "") +
                (correspondentFeeTypeId != null ? "correspondentFeeTypeId=" + correspondentFeeTypeId + ", " : "") +
                (cpiCorrespondentId != null ? "cpiCorrespondentId=" + cpiCorrespondentId + ", " : "") +
            "}";
    }

}
