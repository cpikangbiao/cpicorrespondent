package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.ClubPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClubPerson and its DTO ClubPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {ClubMapper.class})
public interface ClubPersonMapper extends EntityMapper<ClubPersonDTO, ClubPerson> {

    @Mapping(source = "club.id", target = "clubId")
    @Mapping(source = "club.clubName", target = "clubClubName")
    ClubPersonDTO toDto(ClubPerson clubPerson);

    @Mapping(source = "clubId", target = "club")
    ClubPerson toEntity(ClubPersonDTO clubPersonDTO);

    default ClubPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClubPerson clubPerson = new ClubPerson();
        clubPerson.setId(id);
        return clubPerson;
    }
}
