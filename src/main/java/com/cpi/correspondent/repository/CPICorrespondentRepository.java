package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CPICorrespondent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CPICorrespondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPICorrespondentRepository extends JpaRepository<CPICorrespondent, Long>, JpaSpecificationExecutor<CPICorrespondent> {

}
