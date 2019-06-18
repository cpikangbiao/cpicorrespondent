package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.BillFinanceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillFinanceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillFinanceTypeRepository extends JpaRepository<BillFinanceType, Long>, JpaSpecificationExecutor<BillFinanceType> {

}
