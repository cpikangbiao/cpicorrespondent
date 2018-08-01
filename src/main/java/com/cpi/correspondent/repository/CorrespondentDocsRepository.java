package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentDocs;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentDocs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentDocsRepository extends JpaRepository<CorrespondentDocs, Long>, JpaSpecificationExecutor<CorrespondentDocs> {

}
