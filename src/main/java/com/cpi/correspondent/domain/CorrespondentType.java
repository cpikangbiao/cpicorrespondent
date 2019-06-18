package com.cpi.correspondent.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CorrespondentType.
 */
@Entity
@Table(name = "correspondent_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_type_name", nullable = false)
    private String correspondentTypeName;

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

    public String getCorrespondentTypeName() {
        return correspondentTypeName;
    }

    public CorrespondentType correspondentTypeName(String correspondentTypeName) {
        this.correspondentTypeName = correspondentTypeName;
        return this;
    }

    public void setCorrespondentTypeName(String correspondentTypeName) {
        this.correspondentTypeName = correspondentTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public CorrespondentType sortNum(Integer sortNum) {
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
        if (!(o instanceof CorrespondentType)) {
            return false;
        }
        return id != null && id.equals(((CorrespondentType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CorrespondentType{" +
            "id=" + getId() +
            ", correspondentTypeName='" + getCorrespondentTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
