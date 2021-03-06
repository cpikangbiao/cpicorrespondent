package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentBillStatusLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CorrespondentBillStatusLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentBillStatusLogRepository extends JpaRepository<CorrespondentBillStatusLog, Long>, JpaSpecificationExecutor<CorrespondentBillStatusLog> {

    List<CorrespondentBillStatusLog> findByCorrespondentBillId(Long correspondentBillId);
}
