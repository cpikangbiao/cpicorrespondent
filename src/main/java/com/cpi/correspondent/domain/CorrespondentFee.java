package com.cpi.correspondent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A CorrespondentFee.
 */
@Entity
@Table(name = "correspondent_fee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentFee   extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_no")
    private String clientNo;

    @Column(name = "number_id")
    private Integer numberId;

    @Column(name = "currency")
    private Long currency;

    @Column(name = "currency_rate")
    private Float currencyRate;

    @Column(name = "jhi_cost", precision=10, scale=2)
    private BigDecimal cost;

    @Column(name = "cost_date")
    private Instant costDate;

    @Column(name = "cost_dollar", precision=10, scale=2)
    private BigDecimal costDollar;

    @Lob
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    private CorrespondentFeeType correspondentFeeType;

    @ManyToOne
    private CPICorrespondent cpiCorrespondent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientNo() {
        return clientNo;
    }

    public CorrespondentFee clientNo(String clientNo) {
        this.clientNo = clientNo;
        return this;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public CorrespondentFee numberId(Integer numberId) {
        this.numberId = numberId;
        return this;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public Long getCurrency() {
        return currency;
    }

    public CorrespondentFee currency(Long currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Float getCurrencyRate() {
        return currencyRate;
    }

    public CorrespondentFee currencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
        return this;
    }

    public void setCurrencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public CorrespondentFee cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Instant getCostDate() {
        return costDate;
    }

    public CorrespondentFee costDate(Instant costDate) {
        this.costDate = costDate;
        return this;
    }

    public void setCostDate(Instant costDate) {
        this.costDate = costDate;
    }

    public BigDecimal getCostDollar() {
        return costDollar;
    }

    public CorrespondentFee costDollar(BigDecimal costDollar) {
        this.costDollar = costDollar;
        return this;
    }

    public void setCostDollar(BigDecimal costDollar) {
        this.costDollar = costDollar;
    }

    public String getRemark() {
        return remark;
    }

    public CorrespondentFee remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CorrespondentFeeType getCorrespondentFeeType() {
        return correspondentFeeType;
    }

    public CorrespondentFee correspondentFeeType(CorrespondentFeeType correspondentFeeType) {
        this.correspondentFeeType = correspondentFeeType;
        return this;
    }

    public void setCorrespondentFeeType(CorrespondentFeeType correspondentFeeType) {
        this.correspondentFeeType = correspondentFeeType;
    }

    public CPICorrespondent getCpiCorrespondent() {
        return cpiCorrespondent;
    }

    public CorrespondentFee cpiCorrespondent(CPICorrespondent cPICorrespondent) {
        this.cpiCorrespondent = cPICorrespondent;
        return this;
    }

    public void setCpiCorrespondent(CPICorrespondent cPICorrespondent) {
        this.cpiCorrespondent = cPICorrespondent;
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
        CorrespondentFee correspondentFee = (CorrespondentFee) o;
        if (correspondentFee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentFee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentFee{" +
            "id=" + getId() +
            ", clientNo='" + getClientNo() + "'" +
            ", numberId=" + getNumberId() +
            ", currency=" + getCurrency() +
            ", currencyRate=" + getCurrencyRate() +
            ", cost=" + getCost() +
            ", costDate='" + getCostDate() + "'" +
            ", costDollar=" + getCostDollar() +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
