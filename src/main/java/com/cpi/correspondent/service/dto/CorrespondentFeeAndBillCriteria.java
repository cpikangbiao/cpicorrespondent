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
 * Criteria class for the {@link com.cpi.correspondent.domain.CorrespondentFeeAndBill} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CorrespondentFeeAndBillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /correspondent-fee-and-bills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentFeeAndBillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter correspondentDebitBillId;

    private LongFilter correspondentFeeId;

    private LongFilter correspondentCreditBillId;

    public CorrespondentFeeAndBillCriteria(){
    }

    public CorrespondentFeeAndBillCriteria(CorrespondentFeeAndBillCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.correspondentDebitBillId = other.correspondentDebitBillId == null ? null : other.correspondentDebitBillId.copy();
        this.correspondentFeeId = other.correspondentFeeId == null ? null : other.correspondentFeeId.copy();
        this.correspondentCreditBillId = other.correspondentCreditBillId == null ? null : other.correspondentCreditBillId.copy();
    }

    @Override
    public CorrespondentFeeAndBillCriteria copy() {
        return new CorrespondentFeeAndBillCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCorrespondentDebitBillId() {
        return correspondentDebitBillId;
    }

    public void setCorrespondentDebitBillId(LongFilter correspondentDebitBillId) {
        this.correspondentDebitBillId = correspondentDebitBillId;
    }

    public LongFilter getCorrespondentFeeId() {
        return correspondentFeeId;
    }

    public void setCorrespondentFeeId(LongFilter correspondentFeeId) {
        this.correspondentFeeId = correspondentFeeId;
    }

    public LongFilter getCorrespondentCreditBillId() {
        return correspondentCreditBillId;
    }

    public void setCorrespondentCreditBillId(LongFilter correspondentCreditBillId) {
        this.correspondentCreditBillId = correspondentCreditBillId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrespondentFeeAndBillCriteria that = (CorrespondentFeeAndBillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(correspondentDebitBillId, that.correspondentDebitBillId) &&
            Objects.equals(correspondentFeeId, that.correspondentFeeId) &&
            Objects.equals(correspondentCreditBillId, that.correspondentCreditBillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        correspondentDebitBillId,
        correspondentFeeId,
        correspondentCreditBillId
        );
    }

    @Override
    public String toString() {
        return "CorrespondentFeeAndBillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentDebitBillId != null ? "correspondentDebitBillId=" + correspondentDebitBillId + ", " : "") +
                (correspondentFeeId != null ? "correspondentFeeId=" + correspondentFeeId + ", " : "") +
                (correspondentCreditBillId != null ? "correspondentCreditBillId=" + correspondentCreditBillId + ", " : "") +
            "}";
    }

}
