package com.cpi.correspondent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A CorrespondentBill.
 */
@Entity
@Table(name = "correspondent_bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentBill extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_id")
    private Integer numberId;

    @Column(name = "jhi_year")
    private String year;

    @Column(name = "correspondent_bill_code")
    private String correspondentBillCode;

    @Column(name = "correspondent_bill_date")
    private Instant correspondentBillDate;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @Column(name = "currency")
    private Long currency;

    @Column(name = "currency_rate")
    private Float currencyRate;

    @Column(name = "exchange_date")
    private Instant exchangeDate;

    @Column(name = "exchange_currency")
    private Long exchangeCurrency;

    @Column(name = "exchange_rate")
    private Float exchangeRate;

    @Column(name = "exchange_amount", precision=10, scale=2)
    private BigDecimal exchangeAmount;

    @Lob
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    private Credit credit;

    @ManyToOne
    private CPICorrespondent cpiCorrespondent;

    @ManyToOne
    private BillFinanceType billFinanceType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public CorrespondentBill numberId(Integer numberId) {
        this.numberId = numberId;
        return this;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public String getYear() {
        return year;
    }

    public CorrespondentBill year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCorrespondentBillCode() {
        return correspondentBillCode;
    }

    public CorrespondentBill correspondentBillCode(String correspondentBillCode) {
        this.correspondentBillCode = correspondentBillCode;
        return this;
    }

    public void setCorrespondentBillCode(String correspondentBillCode) {
        this.correspondentBillCode = correspondentBillCode;
    }

    public Instant getCorrespondentBillDate() {
        return correspondentBillDate;
    }

    public CorrespondentBill correspondentBillDate(Instant correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
        return this;
    }

    public void setCorrespondentBillDate(Instant correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public CorrespondentBill receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public CorrespondentBill dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CorrespondentBill amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCurrency() {
        return currency;
    }

    public CorrespondentBill currency(Long currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Float getCurrencyRate() {
        return currencyRate;
    }

    public CorrespondentBill currencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
        return this;
    }

    public void setCurrencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Instant getExchangeDate() {
        return exchangeDate;
    }

    public CorrespondentBill exchangeDate(Instant exchangeDate) {
        this.exchangeDate = exchangeDate;
        return this;
    }

    public void setExchangeDate(Instant exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Long getExchangeCurrency() {
        return exchangeCurrency;
    }

    public CorrespondentBill exchangeCurrency(Long exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
        return this;
    }

    public void setExchangeCurrency(Long exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public CorrespondentBill exchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeAmount() {
        return exchangeAmount;
    }

    public CorrespondentBill exchangeAmount(BigDecimal exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
        return this;
    }

    public void setExchangeAmount(BigDecimal exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public String getRemark() {
        return remark;
    }

    public CorrespondentBill remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Credit getCredit() {
        return credit;
    }

    public CorrespondentBill credit(Credit credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public CPICorrespondent getCpiCorrespondent() {
        return cpiCorrespondent;
    }

    public CorrespondentBill cpiCorrespondent(CPICorrespondent cPICorrespondent) {
        this.cpiCorrespondent = cPICorrespondent;
        return this;
    }

    public void setCpiCorrespondent(CPICorrespondent cPICorrespondent) {
        this.cpiCorrespondent = cPICorrespondent;
    }

    public BillFinanceType getBillFinanceType() {
        return billFinanceType;
    }

    public CorrespondentBill billFinanceType(BillFinanceType billFinanceType) {
        this.billFinanceType = billFinanceType;
        return this;
    }

    public void setBillFinanceType(BillFinanceType billFinanceType) {
        this.billFinanceType = billFinanceType;
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
        CorrespondentBill correspondentBill = (CorrespondentBill) o;
        if (correspondentBill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentBill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentBill{" +
            "id=" + getId() +
            ", numberId=" + getNumberId() +
            ", year='" + getYear() + "'" +
            ", correspondentBillCode='" + getCorrespondentBillCode() + "'" +
            ", correspondentBillDate='" + getCorrespondentBillDate() + "'" +
            ", receiver='" + getReceiver() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", amount=" + getAmount() +
            ", currency=" + getCurrency() +
            ", currencyRate=" + getCurrencyRate() +
            ", exchangeDate='" + getExchangeDate() + "'" +
            ", exchangeCurrency=" + getExchangeCurrency() +
            ", exchangeRate=" + getExchangeRate() +
            ", exchangeAmount=" + getExchangeAmount() +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
