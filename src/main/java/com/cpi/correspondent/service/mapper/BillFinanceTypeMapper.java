package com.cpi.correspondent.service.mapper;

import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BillFinanceType} and its DTO {@link BillFinanceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillFinanceTypeMapper extends EntityMapper<BillFinanceTypeDTO, BillFinanceType> {



    default BillFinanceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillFinanceType billFinanceType = new BillFinanceType();
        billFinanceType.setId(id);
        return billFinanceType;
    }
}
