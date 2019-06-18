package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPICorrespondent} and its DTO {@link CPICorrespondentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentTypeMapper.class, ClubMapper.class, ClubPersonMapper.class})
public interface CPICorrespondentMapper extends EntityMapper<CPICorrespondentDTO, CPICorrespondent> {

    @Mapping(source = "correspondentType.id", target = "correspondentTypeId")
    @Mapping(source = "correspondentType.correspondentTypeName", target = "correspondentTypeCorrespondentTypeName")
    @Mapping(source = "club.id", target = "clubId")
    @Mapping(source = "club.clubName", target = "clubClubName")
    @Mapping(source = "clubPerson.id", target = "clubPersonId")
    @Mapping(source = "clubPerson.clubPersonName", target = "clubPersonClubPersonName")
    CPICorrespondentDTO toDto(CPICorrespondent cPICorrespondent);

    @Mapping(source = "correspondentTypeId", target = "correspondentType")
    @Mapping(source = "clubId", target = "club")
    @Mapping(source = "clubPersonId", target = "clubPerson")
    CPICorrespondent toEntity(CPICorrespondentDTO cPICorrespondentDTO);

    default CPICorrespondent fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPICorrespondent cPICorrespondent = new CPICorrespondent();
        cPICorrespondent.setId(id);
        return cPICorrespondent;
    }
}
