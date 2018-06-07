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
 * Criteria class for the CPICorrespondent entity. This class is used in CPICorrespondentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cpi-correspondents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CPICorrespondentCriteria implements Serializable {
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

    public CPICorrespondentCriteria() {
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
