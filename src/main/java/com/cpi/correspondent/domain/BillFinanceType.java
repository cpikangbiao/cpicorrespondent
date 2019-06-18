package com.cpi.correspondent.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A BillFinanceType.
 */
@Entity
@Table(name = "bill_finance_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillFinanceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "bill_finance_type_name", nullable = false)
    private String billFinanceTypeName;

    @NotNull
    @Column(name = "sort_num", nullable = false)
    private Integer sortNum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillFinanceTypeName() {
        return billFinanceTypeName;
    }

    public BillFinanceType billFinanceTypeName(String billFinanceTypeName) {
        this.billFinanceTypeName = billFinanceTypeName;
        return this;
    }

    public void setBillFinanceTypeName(String billFinanceTypeName) {
        this.billFinanceTypeName = billFinanceTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public BillFinanceType sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillFinanceType)) {
            return false;
        }
        return id != null && id.equals(((BillFinanceType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BillFinanceType{" +
            "id=" + getId() +
            ", billFinanceTypeName='" + getBillFinanceTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
