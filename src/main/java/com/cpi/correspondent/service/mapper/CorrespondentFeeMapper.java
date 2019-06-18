package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CorrespondentFee} and its DTO {@link CorrespondentFeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentFeeTypeMapper.class, CPICorrespondentMapper.class})
public interface CorrespondentFeeMapper extends EntityMapper<CorrespondentFeeDTO, CorrespondentFee> {

    @Mapping(source = "correspondentFeeType.id", target = "correspondentFeeTypeId")
    @Mapping(source = "correspondentFeeType.correspondentFeeTypeName", target = "correspondentFeeTypeCorrespondentFeeTypeName")
    @Mapping(source = "cpiCorrespondent.id", target = "cpiCorrespondentId")
    @Mapping(source = "cpiCorrespondent.correspondentCode", target = "cpiCorrespondentCorrespondentCode")
    CorrespondentFeeDTO toDto(CorrespondentFee correspondentFee);

    @Mapping(source = "correspondentFeeTypeId", target = "correspondentFeeType")
    @Mapping(source = "cpiCorrespondentId", target = "cpiCorrespondent")
    CorrespondentFee toEntity(CorrespondentFeeDTO correspondentFeeDTO);

    default CorrespondentFee fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentFee correspondentFee = new CorrespondentFee();
        correspondentFee.setId(id);
        return correspondentFee;
    }
}
