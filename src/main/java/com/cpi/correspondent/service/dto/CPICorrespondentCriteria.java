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
 * Criteria class for the {@link com.cpi.correspondent.domain.CPICorrespondent} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CPICorrespondentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cpi-correspondents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPICorrespondentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter correspondentCode;

    private StringFilter year;

    private StringFilter vesselName;

    private StringFilter clientRef;

    private StringFilter keyWord;

    private InstantFilter registerDate;

    private InstantFilter caseDate;

    private LongFilter handlerUser;

    private LongFilter correspondentTypeId;

    private LongFilter clubId;

    private LongFilter clubPersonId;

    public CPICorrespondentCriteria(){
    }

    public CPICorrespondentCriteria(CPICorrespondentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.correspondentCode = other.correspondentCode == null ? null : other.correspondentCode.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.vesselName = other.vesselName == null ? null : other.vesselName.copy();
        this.clientRef = other.clientRef == null ? null : other.clientRef.copy();
        this.keyWord = other.keyWord == null ? null : other.keyWord.copy();
        this.registerDate = other.registerDate == null ? null : other.registerDate.copy();
        this.caseDate = other.caseDate == null ? null : other.caseDate.copy();
        this.handlerUser = other.handlerUser == null ? null : other.handlerUser.copy();
        this.correspondentTypeId = other.correspondentTypeId == null ? null : other.correspondentTypeId.copy();
        this.clubId = other.clubId == null ? null : other.clubId.copy();
        this.clubPersonId = other.clubPersonId == null ? null : other.clubPersonId.copy();
    }

    @Override
    public CPICorrespondentCriteria copy() {
        return new CPICorrespondentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorrespondentCode() {
        return correspondentCode;
    }

    public void setCorrespondentCode(StringFilter correspondentCode) {
        this.correspondentCode = correspondentCode;
    }

    public StringFilter getYear() {
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getVesselName() {
        return vesselName;
    }

    public void setVesselName(StringFilter vesselName) {
        this.vesselName = vesselName;
    }

    public StringFilter getClientRef() {
        return clientRef;
    }

    public void setClientRef(StringFilter clientRef) {
        this.clientRef = clientRef;
    }

    public StringFilter getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(StringFilter keyWord) {
        this.keyWord = keyWord;
    }

    public InstantFilter getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(InstantFilter registerDate) {
        this.registerDate = registerDate;
    }

    public InstantFilter getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(InstantFilter caseDate) {
        this.caseDate = caseDate;
    }

    public LongFilter getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(LongFilter handlerUser) {
        this.handlerUser = handlerUser;
    }

    public LongFilter getCorrespondentTypeId() {
        return correspondentTypeId;
    }

    public void setCorrespondentTypeId(LongFilter correspondentTypeId) {
        this.correspondentTypeId = correspondentTypeId;
    }

    public LongFilter getClubId() {
        return clubId;
    }

    public void setClubId(LongFilter clubId) {
        this.clubId = clubId;
    }

    public LongFilter getClubPersonId() {
        return clubPersonId;
    }

    public void setClubPersonId(LongFilter clubPersonId) {
        this.clubPersonId = clubPersonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CPICorrespondentCriteria that = (CPICorrespondentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(correspondentCode, that.correspondentCode) &&
            Objects.equals(year, that.year) &&
            Objects.equals(vesselName, that.vesselName) &&
            Objects.equals(clientRef, that.clientRef) &&
            Objects.equals(keyWord, that.keyWord) &&
            Objects.equals(registerDate, that.registerDate) &&
            Objects.equals(caseDate, that.caseDate) &&
            Objects.equals(handlerUser, that.handlerUser) &&
            Objects.equals(correspondentTypeId, that.correspondentTypeId) &&
            Objects.equals(clubId, that.clubId) &&
            Objects.equals(clubPersonId, that.clubPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        correspondentCode,
        year,
        vesselName,
        clientRef,
        keyWord,
        registerDate,
        caseDate,
        handlerUser,
        correspondentTypeId,
        clubId,
        clubPersonId
        );
    }

    @Override
    public String toString() {
        return "CPICorrespondentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentCode != null ? "correspondentCode=" + correspondentCode + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (vesselName != null ? "vesselName=" + vesselName + ", " : "") +
                (clientRef != null ? "clientRef=" + clientRef + ", " : "") +
                (keyWord != null ? "keyWord=" + keyWord + ", " : "") +
                (registerDate != null ? "registerDate=" + registerDate + ", " : "") +
                (caseDate != null ? "caseDate=" + caseDate + ", " : "") +
                (handlerUser != null ? "handlerUser=" + handlerUser + ", " : "") +
                (correspondentTypeId != null ? "correspondentTypeId=" + correspondentTypeId + ", " : "") +
                (clubId != null ? "clubId=" + clubId + ", " : "") +
                (clubPersonId != null ? "clubPersonId=" + clubPersonId + ", " : "") +
            "}";
    }

}
