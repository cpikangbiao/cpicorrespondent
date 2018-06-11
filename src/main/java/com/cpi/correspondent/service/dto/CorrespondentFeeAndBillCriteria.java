package com.cpi.correspondent.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the CorrespondentFeeAndBill entity. This class is used in CorrespondentFeeAndBillResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-fee-and-bills?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentFeeAndBillCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter correspondentDebitBillId;

    private LongFilter correspondentFeeId;

    private LongFilter correspondentCreditBillId;

    public CorrespondentFeeAndBillCriteria() {
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
    public String toString() {
        return "CorrespondentFeeAndBillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentDebitBillId != null ? "correspondentDebitBillId=" + correspondentDebitBillId + ", " : "") +
                (correspondentFeeId != null ? "correspondentFeeId=" + correspondentFeeId + ", " : "") +
                (correspondentCreditBillId != null ? "correspondentCreditBillId=" + correspondentCreditBillId + ", " : "") +
            "}";
    }

}
