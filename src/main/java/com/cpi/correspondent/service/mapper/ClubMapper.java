package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.ClubDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Club and its DTO ClubDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClubMapper extends EntityMapper<ClubDTO, Club> {



    default Club fromId(Long id) {
        if (id == null) {
            return null;
        }
        Club club = new Club();
        club.setId(id);
        return club;
    }
}
