package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.ClubPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClubPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClubPersonRepository extends JpaRepository<ClubPerson, Long>, JpaSpecificationExecutor<ClubPerson> {

}
