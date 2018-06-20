package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentFeeAndBill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentFeeAndBillRepository extends JpaRepository<CorrespondentFeeAndBill, Long>, JpaSpecificationExecutor<CorrespondentFeeAndBill> {

}