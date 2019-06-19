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
 * Criteria class for the {@link com.cpi.correspondent.domain.BillFinanceType} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.BillFinanceTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bill-finance-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BillFinanceTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter billFinanceTypeName;

    private IntegerFilter sortNum;

    public BillFinanceTypeCriteria(){
    }

    public BillFinanceTypeCriteria(BillFinanceTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.billFinanceTypeName = other.billFinanceTypeName == null ? null : other.billFinanceTypeName.copy();
        this.sortNum = other.sortNum == null ? null : other.sortNum.copy();
    }

    @Override
    public BillFinanceTypeCriteria copy() {
        return new BillFinanceTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBillFinanceTypeName() {
        return billFinanceTypeName;
    }

    public void setBillFinanceTypeName(StringFilter billFinanceTypeName) {
        this.billFinanceTypeName = billFinanceTypeName;
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
        final BillFinanceTypeCriteria that = (BillFinanceTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(billFinanceTypeName, that.billFinanceTypeName) &&
            Objects.equals(sortNum, that.sortNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        billFinanceTypeName,
        sortNum
        );
    }

    @Override
    public String toString() {
        return "BillFinanceTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (billFinanceTypeName != null ? "billFinanceTypeName=" + billFinanceTypeName + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
            "}";
    }

}
