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
 * Criteria class for the CorrespondentFee entity. This class is used in CorrespondentFeeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-fees?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentFeeCriteria implements Serializable {
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

    public CorrespondentFeeCriteria() {
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
