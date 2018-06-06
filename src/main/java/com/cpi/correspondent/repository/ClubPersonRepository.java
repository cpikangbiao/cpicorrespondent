package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.ClubPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClubPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClubPersonRepository extends JpaRepository<ClubPerson, Long>, JpaSpecificationExecutor<ClubPerson> {

}
