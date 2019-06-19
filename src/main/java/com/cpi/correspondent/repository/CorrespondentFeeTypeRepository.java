package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentFeeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CorrespondentFeeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentFeeTypeRepository extends JpaRepository<CorrespondentFeeType, Long>, JpaSpecificationExecutor<CorrespondentFeeType> {

}
