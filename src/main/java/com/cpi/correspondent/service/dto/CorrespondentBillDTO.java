package com.cpi.correspondent.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.CorrespondentBill} entity.
 */
public class CorrespondentBillDTO implements Serializable {

    private Long id;

    private Integer numberId;

    private String year;

    private String correspondentBillCode;

    private Instant correspondentBillDate;

    private String receiver;

    private String mainContent;

    private Instant dueDate;

    private BigDecimal amount;

    private Long currency;

    private Float currencyRate;

    private Instant exchangeDate;

    private Long exchangeCurrency;

    private Float exchangeRate;

    private BigDecimal exchangeAmount;

    @Lob
    private String remark;

    private Long correspondentBillStatusId;

    private String correspondentBillStatusCorrespondentBillStatusName;

    private Long creditId;

    private String creditAccountNo;

    private Long cpiCorrespondentId;

    private String cpiCorrespondentCorrespondentCode;

    private Long billFinanceTypeId;

    private String billFinanceTypeBillFinanceTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCorrespondentBillCode() {
        return correspondentBillCode;
    }

    public void setCorrespondentBillCode(String correspondentBillCode) {
        this.correspondentBillCode = correspondentBillCode;
    }

    public Instant getCorrespondentBillDate() {
        return correspondentBillDate;
    }

    public void setCorrespondentBillDate(Instant correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Instant getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Instant exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Long getExchangeCurrency() {
        return exchangeCurrency;
    }

    public void setExchangeCurrency(Long exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(BigDecimal exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCorrespondentBillStatusId() {
        return correspondentBillStatusId;
    }

    public void setCorrespondentBillStatusId(Long correspondentBillStatusId) {
        this.correspondentBillStatusId = correspondentBillStatusId;
    }

    public String getCorrespondentBillStatusCorrespondentBillStatusName() {
        return correspondentBillStatusCorrespondentBillStatusName;
    }

    public void setCorrespondentBillStatusCorrespondentBillStatusName(String correspondentBillStatusCorrespondentBillStatusName) {
        this.correspondentBillStatusCorrespondentBillStatusName = correspondentBillStatusCorrespondentBillStatusName;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public String getCreditAccountNo() {
        return creditAccountNo;
    }

    public void setCreditAccountNo(String creditAccountNo) {
        this.creditAccountNo = creditAccountNo;
    }

    public Long getCpiCorrespondentId() {
        return cpiCorrespondentId;
    }

    public void setCpiCorrespondentId(Long cPICorrespondentId) {
        this.cpiCorrespondentId = cPICorrespondentId;
    }

    public String getCpiCorrespondentCorrespondentCode() {
        return cpiCorrespondentCorrespondentCode;
    }

    public void setCpiCorrespondentCorrespondentCode(String cPICorrespondentCorrespondentCode) {
        this.cpiCorrespondentCorrespondentCode = cPICorrespondentCorrespondentCode;
    }

    public Long getBillFinanceTypeId() {
        return billFinanceTypeId;
    }

    public void setBillFinanceTypeId(Long billFinanceTypeId) {
        this.billFinanceTypeId = billFinanceTypeId;
    }

    public String getBillFinanceTypeBillFinanceTypeName() {
        return billFinanceTypeBillFinanceTypeName;
    }

    public void setBillFinanceTypeBillFinanceTypeName(String billFinanceTypeBillFinanceTypeName) {
        this.billFinanceTypeBillFinanceTypeName = billFinanceTypeBillFinanceTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentBillDTO correspondentBillDTO = (CorrespondentBillDTO) o;
        if (correspondentBillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentBillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentBillDTO{" +
            "id=" + getId() +
            ", numberId=" + getNumberId() +
            ", year='" + getYear() + "'" +
            ", correspondentBillCode='" + getCorrespondentBillCode() + "'" +
            ", correspondentBillDate='" + getCorrespondentBillDate() + "'" +
            ", receiver='" + getReceiver() + "'" +
            ", mainContent='" + getMainContent() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", amount=" + getAmount() +
            ", currency=" + getCurrency() +
            ", currencyRate=" + getCurrencyRate() +
            ", exchangeDate='" + getExchangeDate() + "'" +
            ", exchangeCurrency=" + getExchangeCurrency() +
            ", exchangeRate=" + getExchangeRate() +
            ", exchangeAmount=" + getExchangeAmount() +
            ", remark='" + getRemark() + "'" +
            ", cpiCorrespondent=" + getCpiCorrespondentId() +
            ", cpiCorrespondent='" + getCpiCorrespondentCorrespondentCode() + "'" +
            ", billFinanceType=" + getBillFinanceTypeId() +
            ", billFinanceType='" + getBillFinanceTypeBillFinanceTypeName() + "'" +
            "}";
    }
}
