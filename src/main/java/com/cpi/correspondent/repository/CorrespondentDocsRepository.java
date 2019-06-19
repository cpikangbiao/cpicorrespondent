package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentDocs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CorrespondentDocs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentDocsRepository extends JpaRepository<CorrespondentDocs, Long>, JpaSpecificationExecutor<CorrespondentDocs> {


    List<CorrespondentDocs> findByCpiCorrespondentId(Long cpiCorrespondentId);

}
