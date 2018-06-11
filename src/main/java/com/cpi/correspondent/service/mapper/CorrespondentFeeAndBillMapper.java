package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CorrespondentFeeAndBill and its DTO CorrespondentFeeAndBillDTO.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentBillMapper.class, CorrespondentFeeMapper.class})
public interface CorrespondentFeeAndBillMapper extends EntityMapper<CorrespondentFeeAndBillDTO, CorrespondentFeeAndBill> {

    @Mapping(source = "correspondentDebitBill.id", target = "correspondentDebitBillId")
    @Mapping(source = "correspondentDebitBill.correspondentBillCode", target = "correspondentDebitBillCorrespondentBillCode")
    @Mapping(source = "correspondentFee.id", target = "correspondentFeeId")
    @Mapping(source = "correspondentFee.clientNo", target = "correspondentFeeClientNo")
    @Mapping(source = "correspondentCreditBill.id", target = "correspondentCreditBillId")
    @Mapping(source = "correspondentCreditBill.correspondentBillCode", target = "correspondentCreditBillCorrespondentBillCode")
    CorrespondentFeeAndBillDTO toDto(CorrespondentFeeAndBill correspondentFeeAndBill);

    @Mapping(source = "correspondentDebitBillId", target = "correspondentDebitBill")
    @Mapping(source = "correspondentFeeId", target = "correspondentFee")
    @Mapping(source = "correspondentCreditBillId", target = "correspondentCreditBill")
    CorrespondentFeeAndBill toEntity(CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO);

    default CorrespondentFeeAndBill fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentFeeAndBill correspondentFeeAndBill = new CorrespondentFeeAndBill();
        correspondentFeeAndBill.setId(id);
        return correspondentFeeAndBill;
    }
}
