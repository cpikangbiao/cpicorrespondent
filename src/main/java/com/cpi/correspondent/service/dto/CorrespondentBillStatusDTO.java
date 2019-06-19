package com.cpi.correspondent.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.CorrespondentBillStatus} entity.
 */
public class CorrespondentBillStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentBillStatusName;

    @NotNull
    private Integer sortNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentBillStatusName() {
        return correspondentBillStatusName;
    }

    public void setCorrespondentBillStatusName(String correspondentBillStatusName) {
        this.correspondentBillStatusName = correspondentBillStatusName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentBillStatusDTO correspondentBillStatusDTO = (CorrespondentBillStatusDTO) o;
        if (correspondentBillStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentBillStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentBillStatusDTO{" +
            "id=" + getId() +
            ", correspondentBillStatusName='" + getCorrespondentBillStatusName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
