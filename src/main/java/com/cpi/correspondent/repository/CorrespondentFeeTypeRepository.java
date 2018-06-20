package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentFeeType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentFeeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentFeeTypeRepository extends JpaRepository<CorrespondentFeeType, Long>, JpaSpecificationExecutor<CorrespondentFeeType> {

}
