package com.cpi.correspondent.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the CorrespondentFee entity.
 */
public class CorrespondentFeeDTO implements Serializable {

    private Long id;

    private String clientNo;

    private Integer numberId;

    private Long currency;

    private Float currencyRate;

    private BigDecimal cost;

    private Instant costDate;

    private BigDecimal costDollar;

    @Lob
    private String remark;

    private Long correspondentFeeTypeId;

    private String correspondentFeeTypeCorrespondentFeeTypeName;

    private Long cpiCorrespondentId;

    private String cpiCorrespondentCorrespondentCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Instant getCostDate() {
        return costDate;
    }

    public void setCostDate(Instant costDate) {
        this.costDate = costDate;
    }

    public BigDecimal getCostDollar() {
        return costDollar;
    }

    public void setCostDollar(BigDecimal costDollar) {
        this.costDollar = costDollar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCorrespondentFeeTypeId() {
        return correspondentFeeTypeId;
    }

    public void setCorrespondentFeeTypeId(Long correspondentFeeTypeId) {
        this.correspondentFeeTypeId = correspondentFeeTypeId;
    }

    public String getCorrespondentFeeTypeCorrespondentFeeTypeName() {
        return correspondentFeeTypeCorrespondentFeeTypeName;
    }

    public void setCorrespondentFeeTypeCorrespondentFeeTypeName(String correspondentFeeTypeCorrespondentFeeTypeName) {
        this.correspondentFeeTypeCorrespondentFeeTypeName = correspondentFeeTypeCorrespondentFeeTypeName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentFeeDTO correspondentFeeDTO = (CorrespondentFeeDTO) o;
        if(correspondentFeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentFeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentFeeDTO{" +
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
