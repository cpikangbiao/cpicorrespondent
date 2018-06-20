package com.cpi.correspondent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClubPerson.
 */
@Entity
@Table(name = "club_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClubPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "club_person_name", nullable = false)
    private String clubPersonName;

    @Column(name = "url")
    private String url;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "address")
    private String address;

    @Column(name = "zip")
    private String zip;

    @Lob
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    private Club club;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClubPersonName() {
        return clubPersonName;
    }

    public ClubPerson clubPersonName(String clubPersonName) {
        this.clubPersonName = clubPersonName;
        return this;
    }

    public void setClubPersonName(String clubPersonName) {
        this.clubPersonName = clubPersonName;
    }

    public String getUrl() {
        return url;
    }

    public ClubPerson url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public ClubPerson email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public ClubPerson phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public ClubPerson fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public ClubPerson mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public ClubPerson address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public ClubPerson zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRemark() {
        return remark;
    }

    public ClubPerson remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Club getClub() {
        return club;
    }

    public ClubPerson club(Club club) {
        this.club = club;
        return this;
    }

    public void setClub(Club club) {
        this.club = club;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClubPerson clubPerson = (ClubPerson) o;
        if (clubPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clubPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClubPerson{" +
            "id=" + getId() +
            ", clubPersonName='" + getClubPersonName() + "'" +
            ", url='" + getUrl() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", zip='" + getZip() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
