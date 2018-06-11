package com.cpi.correspondent.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BillFinanceType entity.
 */
public class BillFinanceTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String billFinanceTypeName;

    @NotNull
    private Integer sortNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillFinanceTypeName() {
        return billFinanceTypeName;
    }

    public void setBillFinanceTypeName(String billFinanceTypeName) {
        this.billFinanceTypeName = billFinanceTypeName;
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

        BillFinanceTypeDTO billFinanceTypeDTO = (BillFinanceTypeDTO) o;
        if(billFinanceTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billFinanceTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillFinanceTypeDTO{" +
            "id=" + getId() +
            ", billFinanceTypeName='" + getBillFinanceTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
