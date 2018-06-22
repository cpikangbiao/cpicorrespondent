package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentBill;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentBill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentBillRepository extends JpaRepository<CorrespondentBill, Long>, JpaSpecificationExecutor<CorrespondentBill> {

//    Integer getMaxNumberIdByYear(String year);

    CorrespondentBill findTopByYearOrderByNumberIdDesc(String year);
}
