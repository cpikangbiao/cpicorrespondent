package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CorrespondentType} and its DTO {@link CorrespondentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorrespondentTypeMapper extends EntityMapper<CorrespondentTypeDTO, CorrespondentType> {



    default CorrespondentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentType correspondentType = new CorrespondentType();
        correspondentType.setId(id);
        return correspondentType;
    }
}
