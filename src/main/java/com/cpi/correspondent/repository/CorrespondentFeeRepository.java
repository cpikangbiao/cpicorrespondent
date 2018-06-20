package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentFee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentFee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentFeeRepository extends JpaRepository<CorrespondentFee, Long>, JpaSpecificationExecutor<CorrespondentFee> {

}
