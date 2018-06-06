package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentTypeRepository extends JpaRepository<CorrespondentType, Long>, JpaSpecificationExecutor<CorrespondentType> {

}
