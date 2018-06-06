package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.Correspondent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Correspondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentRepository extends JpaRepository<Correspondent, Long>, JpaSpecificationExecutor<Correspondent> {

}
