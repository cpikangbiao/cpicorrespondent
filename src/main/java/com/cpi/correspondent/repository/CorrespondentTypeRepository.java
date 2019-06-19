package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CorrespondentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentTypeRepository extends JpaRepository<CorrespondentType, Long>, JpaSpecificationExecutor<CorrespondentType> {

}
