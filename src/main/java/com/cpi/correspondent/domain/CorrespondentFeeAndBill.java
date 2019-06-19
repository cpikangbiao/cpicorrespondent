package com.cpi.correspondent.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CorrespondentFeeAndBill.
 */
@Entity
@Table(name = "correspondent_fee_and_bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentFeeAndBill  extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("correspondentFeeAndBills")
    private CorrespondentBill correspondentDebitBill;

    @ManyToOne
    @JsonIgnoreProperties("correspondentFeeAndBills")
    private CorrespondentFee correspondentFee;

    @ManyToOne
    @JsonIgnoreProperties("correspondentFeeAndBills")
    private CorrespondentBill correspondentCreditBill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorrespondentBill getCorrespondentDebitBill() {
        return correspondentDebitBill;
    }

    public CorrespondentFeeAndBill correspondentDebitBill(CorrespondentBill correspondentBill) {
        this.correspondentDebitBill = correspondentBill;
        return this;
    }

    public void setCorrespondentDebitBill(CorrespondentBill correspondentBill) {
        this.correspondentDebitBill = correspondentBill;
    }

    public CorrespondentFee getCorrespondentFee() {
        return correspondentFee;
    }

    public CorrespondentFeeAndBill correspondentFee(CorrespondentFee correspondentFee) {
        this.correspondentFee = correspondentFee;
        return this;
    }

    public void setCorrespondentFee(CorrespondentFee correspondentFee) {
        this.correspondentFee = correspondentFee;
    }

    public CorrespondentBill getCorrespondentCreditBill() {
        return correspondentCreditBill;
    }

    public CorrespondentFeeAndBill correspondentCreditBill(CorrespondentBill correspondentBill) {
        this.correspondentCreditBill = correspondentBill;
        return this;
    }

    public void setCorrespondentCreditBill(CorrespondentBill correspondentBill) {
        this.correspondentCreditBill = correspondentBill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CorrespondentFeeAndBill)) {
            return false;
        }
        return id != null && id.equals(((CorrespondentFeeAndBill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CorrespondentFeeAndBill{" +
            "id=" + getId() +
            "}";
    }
}
