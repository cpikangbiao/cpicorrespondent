package com.cpi.correspondent.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.correspondent.domain.CorrespondentFeeAndBill} entity.
 */
public class CorrespondentFeeAndBillDTO implements Serializable {

    private Long id;


    private Long correspondentDebitBillId;

    private String correspondentDebitBillCorrespondentBillCode;

    private Long correspondentFeeId;

    private String correspondentFeeClientNo;

    private Long correspondentCreditBillId;

    private String correspondentCreditBillCorrespondentBillCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCorrespondentDebitBillId() {
        return correspondentDebitBillId;
    }

    public void setCorrespondentDebitBillId(Long correspondentBillId) {
        this.correspondentDebitBillId = correspondentBillId;
    }

    public String getCorrespondentDebitBillCorrespondentBillCode() {
        return correspondentDebitBillCorrespondentBillCode;
    }

    public void setCorrespondentDebitBillCorrespondentBillCode(String correspondentBillCorrespondentBillCode) {
        this.correspondentDebitBillCorrespondentBillCode = correspondentBillCorrespondentBillCode;
    }

    public Long getCorrespondentFeeId() {
        return correspondentFeeId;
    }

    public void setCorrespondentFeeId(Long correspondentFeeId) {
        this.correspondentFeeId = correspondentFeeId;
    }

    public String getCorrespondentFeeClientNo() {
        return correspondentFeeClientNo;
    }

    public void setCorrespondentFeeClientNo(String correspondentFeeClientNo) {
        this.correspondentFeeClientNo = correspondentFeeClientNo;
    }

    public Long getCorrespondentCreditBillId() {
        return correspondentCreditBillId;
    }

    public void setCorrespondentCreditBillId(Long correspondentBillId) {
        this.correspondentCreditBillId = correspondentBillId;
    }

    public String getCorrespondentCreditBillCorrespondentBillCode() {
        return correspondentCreditBillCorrespondentBillCode;
    }

    public void setCorrespondentCreditBillCorrespondentBillCode(String correspondentBillCorrespondentBillCode) {
        this.correspondentCreditBillCorrespondentBillCode = correspondentBillCorrespondentBillCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO = (CorrespondentFeeAndBillDTO) o;
        if (correspondentFeeAndBillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentFeeAndBillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentFeeAndBillDTO{" +
            "id=" + getId() +
            ", correspondentDebitBill=" + getCorrespondentDebitBillId() +
            ", correspondentDebitBill='" + getCorrespondentDebitBillCorrespondentBillCode() + "'" +
            ", correspondentFee=" + getCorrespondentFeeId() +
            ", correspondentFee='" + getCorrespondentFeeClientNo() + "'" +
            ", correspondentCreditBill=" + getCorrespondentCreditBillId() +
            ", correspondentCreditBill='" + getCorrespondentCreditBillCorrespondentBillCode() + "'" +
            "}";
    }
}
