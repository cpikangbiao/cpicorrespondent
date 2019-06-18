package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CorrespondentBill} and its DTO {@link CorrespondentBillDTO}.
 */
@Mapper(componentModel = "spring", uses = {CPICorrespondentMapper.class, BillFinanceTypeMapper.class})
public interface CorrespondentBillMapper extends EntityMapper<CorrespondentBillDTO, CorrespondentBill> {

    @Mapping(source = "cpiCorrespondent.id", target = "cpiCorrespondentId")
    @Mapping(source = "cpiCorrespondent.correspondentCode", target = "cpiCorrespondentCorrespondentCode")
    @Mapping(source = "billFinanceType.id", target = "billFinanceTypeId")
    @Mapping(source = "billFinanceType.billFinanceTypeName", target = "billFinanceTypeBillFinanceTypeName")
    CorrespondentBillDTO toDto(CorrespondentBill correspondentBill);

    @Mapping(source = "cpiCorrespondentId", target = "cpiCorrespondent")
    @Mapping(source = "billFinanceTypeId", target = "billFinanceType")
    CorrespondentBill toEntity(CorrespondentBillDTO correspondentBillDTO);

    default CorrespondentBill fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentBill correspondentBill = new CorrespondentBill();
        correspondentBill.setId(id);
        return correspondentBill;
    }
}
