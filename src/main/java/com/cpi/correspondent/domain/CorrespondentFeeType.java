package com.cpi.correspondent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CorrespondentFeeType.
 */
@Entity
@Table(name = "correspondent_fee_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentFeeType implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long CORRESPONDENT_FEE_TYPE_CORRESPONDENT = new Long(5);

    public static final Long CORRESPONDENT_FEE_TYPE_OTHER = new Long(4);

    public static final Long CORRESPONDENT_FEE_TYPE_SURVEYOR = new Long(1);

    public static final Long CORRESPONDENT_FEE_TYPE_LAWAY = new Long(2);

    public static final Long CORRESPONDENT_FEE_TYPE_EXPERT = new Long(3);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_fee_type_name", nullable = false)
    private String correspondentFeeTypeName;

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

    public String getCorrespondentFeeTypeName() {
        return correspondentFeeTypeName;
    }

    public CorrespondentFeeType correspondentFeeTypeName(String correspondentFeeTypeName) {
        this.correspondentFeeTypeName = correspondentFeeTypeName;
        return this;
    }

    public void setCorrespondentFeeTypeName(String correspondentFeeTypeName) {
        this.correspondentFeeTypeName = correspondentFeeTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public CorrespondentFeeType sortNum(Integer sortNum) {
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorrespondentFeeType correspondentFeeType = (CorrespondentFeeType) o;
        if (correspondentFeeType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentFeeType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentFeeType{" +
            "id=" + getId() +
            ", correspondentFeeTypeName='" + getCorrespondentFeeTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
