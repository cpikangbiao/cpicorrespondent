package com.cpi.correspondent.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.CorrespondentFeeType} entity.
 */
public class CorrespondentFeeTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentFeeTypeName;

    @NotNull
    private Integer sortNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentFeeTypeName() {
        return correspondentFeeTypeName;
    }

    public void setCorrespondentFeeTypeName(String correspondentFeeTypeName) {
        this.correspondentFeeTypeName = correspondentFeeTypeName;
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

        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = (CorrespondentFeeTypeDTO) o;
        if (correspondentFeeTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentFeeTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentFeeTypeDTO{" +
            "id=" + getId() +
            ", correspondentFeeTypeName='" + getCorrespondentFeeTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
