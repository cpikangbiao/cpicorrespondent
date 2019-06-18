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
 * Criteria class for the {@link com.cpi.correspondent.domain.Club} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.ClubResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clubs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClubCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clubName;

    private StringFilter url;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter fax;

    private StringFilter mobilePhone;

    private StringFilter address;

    private StringFilter zip;

    public ClubCriteria(){
    }

    public ClubCriteria(ClubCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.clubName = other.clubName == null ? null : other.clubName.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.mobilePhone = other.mobilePhone == null ? null : other.mobilePhone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.zip = other.zip == null ? null : other.zip.copy();
    }

    @Override
    public ClubCriteria copy() {
        return new ClubCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClubName() {
        return clubName;
    }

    public void setClubName(StringFilter clubName) {
        this.clubName = clubName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClubCriteria that = (ClubCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clubName, that.clubName) &&
            Objects.equals(url, that.url) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(mobilePhone, that.mobilePhone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(zip, that.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clubName,
        url,
        email,
        phone,
        fax,
        mobilePhone,
        address,
        zip
        );
    }

    @Override
    public String toString() {
        return "ClubCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clubName != null ? "clubName=" + clubName + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (mobilePhone != null ? "mobilePhone=" + mobilePhone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (zip != null ? "zip=" + zip + ", " : "") +
            "}";
    }

}
