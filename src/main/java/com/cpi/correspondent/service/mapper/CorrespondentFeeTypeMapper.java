package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CorrespondentFeeType} and its DTO {@link CorrespondentFeeTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorrespondentFeeTypeMapper extends EntityMapper<CorrespondentFeeTypeDTO, CorrespondentFeeType> {



    default CorrespondentFeeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentFeeType correspondentFeeType = new CorrespondentFeeType();
        correspondentFeeType.setId(id);
        return correspondentFeeType;
    }
}
