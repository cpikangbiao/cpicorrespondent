package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Correspondent and its DTO CorrespondentDTO.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentTypeMapper.class, ClubMapper.class, ClubPersonMapper.class})
public interface CorrespondentMapper extends EntityMapper<CorrespondentDTO, Correspondent> {

    @Mapping(source = "correspondentType.id", target = "correspondentTypeId")
    @Mapping(source = "correspondentType.correspondentTypeName", target = "correspondentTypeCorrespondentTypeName")
    @Mapping(source = "club.id", target = "clubId")
    @Mapping(source = "club.clubName", target = "clubClubName")
    @Mapping(source = "clubPerson.id", target = "clubPersonId")
    @Mapping(source = "clubPerson.clubPersonName", target = "clubPersonClubPersonName")
    CorrespondentDTO toDto(Correspondent correspondent);

    @Mapping(source = "correspondentTypeId", target = "correspondentType")
    @Mapping(source = "clubId", target = "club")
    @Mapping(source = "clubPersonId", target = "clubPerson")
    Correspondent toEntity(CorrespondentDTO correspondentDTO);

    default Correspondent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Correspondent correspondent = new Correspondent();
        correspondent.setId(id);
        return correspondent;
    }
}
