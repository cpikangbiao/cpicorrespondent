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
 * Criteria class for the ClubPerson entity. This class is used in ClubPersonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /club-people?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClubPersonCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter clubPersonName;

    private StringFilter url;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter fax;

    private StringFilter mobilePhone;

    private StringFilter address;

    private StringFilter zip;

    private LongFilter clubId;

    public ClubPersonCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClubPersonName() {
        return clubPersonName;
    }

    public void setClubPersonName(StringFilter clubPersonName) {
        this.clubPersonName = clubPersonName;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(StringFilter mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getZip() {
        return zip;
    }

    public void setZip(StringFilter zip) {
        this.zip = zip;
    }

    public LongFilter getClubId() {
        return clubId;
    }

    public void setClubId(LongFilter clubId) {
        this.clubId = clubId;
    }

    @Override
    public String toString() {
        return "ClubPersonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clubPersonName != null ? "clubPersonName=" + clubPersonName + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (mobilePhone != null ? "mobilePhone=" + mobilePhone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (zip != null ? "zip=" + zip + ", " : "") +
                (clubId != null ? "clubId=" + clubId + ", " : "") +
            "}";
    }

}
