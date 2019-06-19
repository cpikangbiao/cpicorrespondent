package com.cpi.correspondent.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.ClubPerson} entity.
 */
public class ClubPersonDTO implements Serializable {

    private Long id;

    @NotNull
    private String clubPersonName;

    private String url;

    private String email;

    private String phone;

    private String fax;

    private String mobilePhone;

    private String address;

    private String zip;

    @Lob
    private String remark;


    private Long clubId;

    private String clubClubName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClubPersonName() {
        return clubPersonName;
    }

    public void setClubPersonName(String clubPersonName) {
        this.clubPersonName = clubPersonName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getClubClubName() {
        return clubClubName;
    }

    public void setClubClubName(String clubClubName) {
        this.clubClubName = clubClubName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClubPersonDTO clubPersonDTO = (ClubPersonDTO) o;
        if (clubPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clubPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClubPersonDTO{" +
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
            ", club=" + getClubId() +
            ", club='" + getClubClubName() + "'" +
            "}";
    }
}
