package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CorrespondentBillStatus and its DTO CorrespondentBillStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorrespondentBillStatusMapper extends EntityMapper<CorrespondentBillStatusDTO, CorrespondentBillStatus> {



    default CorrespondentBillStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentBillStatus correspondentBillStatus = new CorrespondentBillStatus();
        correspondentBillStatus.setId(id);
        return correspondentBillStatus;
    }
}
