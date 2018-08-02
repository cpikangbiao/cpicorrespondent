package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CorrespondentBillStatusLog and its DTO CorrespondentBillStatusLogDTO.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentBillMapper.class})
public interface CorrespondentBillStatusLogMapper extends EntityMapper<CorrespondentBillStatusLogDTO, CorrespondentBillStatusLog> {

    @Mapping(source = "correspondentBill.id", target = "correspondentBillId")
    @Mapping(source = "correspondentBill.correspondentBillCode", target = "correspondentBillCorrespondentBillCode")
    CorrespondentBillStatusLogDTO toDto(CorrespondentBillStatusLog correspondentBillStatusLog);

    @Mapping(source = "correspondentBillId", target = "correspondentBill")
    CorrespondentBillStatusLog toEntity(CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO);

    default CorrespondentBillStatusLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentBillStatusLog correspondentBillStatusLog = new CorrespondentBillStatusLog();
        correspondentBillStatusLog.setId(id);
        return correspondentBillStatusLog;
    }
}
