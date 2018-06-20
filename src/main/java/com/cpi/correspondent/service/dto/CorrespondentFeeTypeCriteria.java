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
 * Criteria class for the CorrespondentFeeType entity. This class is used in CorrespondentFeeTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-fee-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentFeeTypeCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter correspondentFeeTypeName;

    private IntegerFilter sortNum;

    public CorrespondentFeeTypeCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorrespondentFeeTypeName() {
        return correspondentFeeTypeName;
    }

    public void setCorrespondentFeeTypeName(StringFilter correspondentFeeTypeName) {
        this.correspondentFeeTypeName = correspondentFeeTypeName;
    }

    public IntegerFilter getSortNum() {
        return sortNum;
    }

    public void setSortNum(IntegerFilter sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "CorrespondentFeeTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentFeeTypeName != null ? "correspondentFeeTypeName=" + correspondentFeeTypeName + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
            "}";
    }

}
