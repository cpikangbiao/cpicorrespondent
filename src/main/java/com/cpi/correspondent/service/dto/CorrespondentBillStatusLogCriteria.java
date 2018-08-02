package com.cpi.correspondent.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the CorrespondentBillStatusLog entity. This class is used in CorrespondentBillStatusLogResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-bill-status-logs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentBillStatusLogCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter billStatusName;

    private InstantFilter updateTime;

    private InstantFilter updateUser;

    private LongFilter correspondentBillId;

    public CorrespondentBillStatusLogCriteria() {
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

    public InstantFilter getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(InstantFilter updateUser) {
        this.updateUser = updateUser;
    }

    public LongFilter getCorrespondentBillId() {
        return correspondentBillId;
    }

    public void setCorrespondentBillId(LongFilter correspondentBillId) {
        this.correspondentBillId = correspondentBillId;
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
