package com.cpi.correspondent.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CorrespondentBillStatus.
 */
@Entity
@Table(name = "correspondent_bill_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentBillStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public  static final long CORRESPONDENT_BILL_STATUS_NOPAID = 1;

    public  static final long CORRESPONDENT_BILL_STATUS_PAIDED = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_bill_status_name", nullable = false)
    private String correspondentBillStatusName;

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

    public String getCorrespondentBillStatusName() {
        return correspondentBillStatusName;
    }

    public CorrespondentBillStatus correspondentBillStatusName(String correspondentBillStatusName) {
        this.correspondentBillStatusName = correspondentBillStatusName;
        return this;
    }

    public void setCorrespondentBillStatusName(String correspondentBillStatusName) {
        this.correspondentBillStatusName = correspondentBillStatusName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public CorrespondentBillStatus sortNum(Integer sortNum) {
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
        if (!(o instanceof CorrespondentBillStatus)) {
            return false;
        }
        return id != null && id.equals(((CorrespondentBillStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CorrespondentBillStatus{" +
            "id=" + getId() +
            ", correspondentBillStatusName='" + getCorrespondentBillStatusName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
