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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cpi.correspondent.domain.CorrespondentBillStatusLog} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CorrespondentBillStatusLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /correspondent-bill-status-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentBillStatusLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter billStatusName;

    private InstantFilter updateTime;

    private LongFilter updateUser;

    private LongFilter correspondentBillId;

    public CorrespondentBillStatusLogCriteria(){
    }

    public CorrespondentBillStatusLogCriteria(CorrespondentBillStatusLogCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.billStatusName = other.billStatusName == null ? null : other.billStatusName.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.updateUser = other.updateUser == null ? null : other.updateUser.copy();
        this.correspondentBillId = other.correspondentBillId == null ? null : other.correspondentBillId.copy();
    }

    @Override
    public CorrespondentBillStatusLogCriteria copy() {
        return new CorrespondentBillStatusLogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBillStatusName() {
        return billStatusName;
    }

    public void setBillStatusName(StringFilter billStatusName) {
        this.billStatusName = billStatusName;
    }

    public InstantFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(InstantFilter updateTime) {
        this.updateTime = updateTime;
    }

    public LongFilter getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(LongFilter updateUser) {
        this.updateUser = updateUser;
    }

    public LongFilter getCorrespondentBillId() {
        return correspondentBillId;
    }

    public void setCorrespondentBillId(LongFilter correspondentBillId) {
        this.correspondentBillId = correspondentBillId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrespondentBillStatusLogCriteria that = (CorrespondentBillStatusLogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(billStatusName, that.billStatusName) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(correspondentBillId, that.correspondentBillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        billStatusName,
        updateTime,
        updateUser,
        correspondentBillId
        );
    }

    @Override
    public String toString() {
        return "CorrespondentBillStatusLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (billStatusName != null ? "billStatusName=" + billStatusName + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (updateUser != null ? "updateUser=" + updateUser + ", " : "") +
                (correspondentBillId != null ? "correspondentBillId=" + correspondentBillId + ", " : "") +
            "}";
    }

}
