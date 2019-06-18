package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentBill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CorrespondentBill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentBillRepository extends JpaRepository<CorrespondentBill, Long>, JpaSpecificationExecutor<CorrespondentBill> {

}
