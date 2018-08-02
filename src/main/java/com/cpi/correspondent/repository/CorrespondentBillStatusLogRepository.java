package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentBillStatusLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentBillStatusLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentBillStatusLogRepository extends JpaRepository<CorrespondentBillStatusLog, Long>, JpaSpecificationExecutor<CorrespondentBillStatusLog> {

}
