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
 * Criteria class for the {@link com.cpi.correspondent.domain.CorrespondentBillStatus} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CorrespondentBillStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /correspondent-bill-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentBillStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter correspondentBillStatusName;

    private IntegerFilter sortNum;

    public CorrespondentBillStatusCriteria(){
    }

    public CorrespondentBillStatusCriteria(CorrespondentBillStatusCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.correspondentBillStatusName = other.correspondentBillStatusName == null ? null : other.correspondentBillStatusName.copy();
        this.sortNum = other.sortNum == null ? null : other.sortNum.copy();
    }

    @Override
    public CorrespondentBillStatusCriteria copy() {
        return new CorrespondentBillStatusCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorrespondentBillStatusName() {
        return correspondentBillStatusName;
    }

    public void setCorrespondentBillStatusName(StringFilter correspondentBillStatusName) {
        this.correspondentBillStatusName = correspondentBillStatusName;
    }

    public IntegerFilter getSortNum() {
        return sortNum;
    }

    public void setSortNum(IntegerFilter sortNum) {
        this.sortNum = sortNum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrespondentBillStatusCriteria that = (CorrespondentBillStatusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(correspondentBillStatusName, that.correspondentBillStatusName) &&
            Objects.equals(sortNum, that.sortNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        correspondentBillStatusName,
        sortNum
        );
    }

    @Override
    public String toString() {
        return "CorrespondentBillStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentBillStatusName != null ? "correspondentBillStatusName=" + correspondentBillStatusName + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
            "}";
    }

}
