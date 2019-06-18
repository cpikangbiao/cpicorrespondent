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
 * Criteria class for the {@link com.cpi.correspondent.domain.ClubPerson} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.ClubPersonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /club-people?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClubPersonCriteria implements Serializable, Criteria {

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

    public ClubPersonCriteria(){
    }

    public ClubPersonCriteria(ClubPersonCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.clubPersonName = other.clubPersonName == null ? null : other.clubPersonName.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.mobilePhone = other.mobilePhone == null ? null : other.mobilePhone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.zip = other.zip == null ? null : other.zip.copy();
        this.clubId = other.clubId == null ? null : other.clubId.copy();
    }

    @Override
    public ClubPersonCriteria copy() {
        return new ClubPersonCriteria(this);
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClubPersonCriteria that = (ClubPersonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clubPersonName, that.clubPersonName) &&
            Objects.equals(url, that.url) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(mobilePhone, that.mobilePhone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(zip, that.zip) &&
            Objects.equals(clubId, that.clubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clubPersonName,
        url,
        email,
        phone,
        fax,
        mobilePhone,
        address,
        zip,
        clubId
        );
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
