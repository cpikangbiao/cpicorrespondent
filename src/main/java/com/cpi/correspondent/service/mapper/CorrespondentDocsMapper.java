package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CorrespondentDocs and its DTO CorrespondentDocsDTO.
 */
@Mapper(componentModel = "spring", uses = {CPICorrespondentMapper.class})
public interface CorrespondentDocsMapper extends EntityMapper<CorrespondentDocsDTO, CorrespondentDocs> {

    @Mapping(source = "cpiCorrespondent.id", target = "cpiCorrespondentId")
    @Mapping(source = "cpiCorrespondent.correspondentCode", target = "cpiCorrespondentCorrespondentCode")
    CorrespondentDocsDTO toDto(CorrespondentDocs correspondentDocs);

    @Mapping(source = "cpiCorrespondentId", target = "cpiCorrespondent")
    CorrespondentDocs toEntity(CorrespondentDocsDTO correspondentDocsDTO);

    default CorrespondentDocs fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentDocs correspondentDocs = new CorrespondentDocs();
        correspondentDocs.setId(id);
        return correspondentDocs;
    }
}
