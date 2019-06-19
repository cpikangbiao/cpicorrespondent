package com.cpi.correspondent.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.CorrespondentType} entity.
 */
public class CorrespondentTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentTypeName;

    @NotNull
    private Integer sortNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentTypeName() {
        return correspondentTypeName;
    }

    public void setCorrespondentTypeName(String correspondentTypeName) {
        this.correspondentTypeName = correspondentTypeName;
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

        CorrespondentTypeDTO correspondentTypeDTO = (CorrespondentTypeDTO) o;
        if (correspondentTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentTypeDTO{" +
            "id=" + getId() +
            ", correspondentTypeName='" + getCorrespondentTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
