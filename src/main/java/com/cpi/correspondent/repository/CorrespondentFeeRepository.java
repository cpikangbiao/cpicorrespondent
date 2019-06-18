package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentFee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CorrespondentFee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentFeeRepository extends JpaRepository<CorrespondentFee, Long>, JpaSpecificationExecutor<CorrespondentFee> {

}
