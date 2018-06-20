package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CPICorrespondent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CPICorrespondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPICorrespondentRepository extends JpaRepository<CPICorrespondent, Long>, JpaSpecificationExecutor<CPICorrespondent> {

}
