package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.CreditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Credit} and its DTO {@link CreditDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditMapper extends EntityMapper<CreditDTO, Credit> {



    default Credit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credit credit = new Credit();
        credit.setId(id);
        return credit;
    }
}
