package com.cpi.correspondent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CorrespondentBillStatusLog.
 */
@Entity
@Table(name = "correspondent_bill_status_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentBillStatusLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "bill_status_name", nullable = false)
    private String billStatusName;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @Column(name = "update_user")
    private Long updateUser;

    @ManyToOne
    private CorrespondentBill correspondentBill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillStatusName() {
        return billStatusName;
    }

    public CorrespondentBillStatusLog billStatusName(String billStatusName) {
        this.billStatusName = billStatusName;
        return this;
    }

    public void setBillStatusName(String billStatusName) {
        this.billStatusName = billStatusName;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public CorrespondentBillStatusLog updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public CorrespondentBillStatusLog updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public CorrespondentBill getCorrespondentBill() {
        return correspondentBill;
    }

    public CorrespondentBillStatusLog correspondentBill(CorrespondentBill correspondentBill) {
        this.correspondentBill = correspondentBill;
        return this;
    }

    public void setCorrespondentBill(CorrespondentBill correspondentBill) {
        this.correspondentBill = correspondentBill;
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
        CorrespondentBillStatusLog correspondentBillStatusLog = (CorrespondentBillStatusLog) o;
        if (correspondentBillStatusLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentBillStatusLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentBillStatusLog{" +
            "id=" + getId() +
            ", billStatusName='" + getBillStatusName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser=" + getUpdateUser() +
            "}";
    }
}
