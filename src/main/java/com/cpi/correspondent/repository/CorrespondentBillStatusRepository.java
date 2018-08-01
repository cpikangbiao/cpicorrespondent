package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentBillStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentBillStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentBillStatusRepository extends JpaRepository<CorrespondentBillStatus, Long>, JpaSpecificationExecutor<CorrespondentBillStatus> {

}
