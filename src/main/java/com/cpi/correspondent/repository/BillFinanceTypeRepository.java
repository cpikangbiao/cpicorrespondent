package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.BillFinanceType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BillFinanceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillFinanceTypeRepository extends JpaRepository<BillFinanceType, Long>, JpaSpecificationExecutor<BillFinanceType> {

}
