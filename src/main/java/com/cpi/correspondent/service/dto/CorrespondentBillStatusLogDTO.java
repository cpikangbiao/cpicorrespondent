package com.cpi.correspondent.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.CorrespondentBillStatusLog} entity.
 */
public class CorrespondentBillStatusLogDTO implements Serializable {

    private Long id;

    @NotNull
    private String billStatusName;

    @NotNull
    private Instant updateTime;

    private Long updateUser;


    private Long correspondentBillId;

    private String correspondentBillCorrespondentBillCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillStatusName() {
        return billStatusName;
    }

    public void setBillStatusName(String billStatusName) {
        this.billStatusName = billStatusName;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Long getCorrespondentBillId() {
        return correspondentBillId;
    }

    public void setCorrespondentBillId(Long correspondentBillId) {
        this.correspondentBillId = correspondentBillId;
    }

    public String getCorrespondentBillCorrespondentBillCode() {
        return correspondentBillCorrespondentBillCode;
    }

    public void setCorrespondentBillCorrespondentBillCode(String correspondentBillCorrespondentBillCode) {
        this.correspondentBillCorrespondentBillCode = correspondentBillCorrespondentBillCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = (CorrespondentBillStatusLogDTO) o;
        if (correspondentBillStatusLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentBillStatusLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentBillStatusLogDTO{" +
            "id=" + getId() +
            ", billStatusName='" + getBillStatusName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", correspondentBill=" + getCorrespondentBillId() +
            ", correspondentBill='" + getCorrespondentBillCorrespondentBillCode() + "'" +
            "}";
    }
}
