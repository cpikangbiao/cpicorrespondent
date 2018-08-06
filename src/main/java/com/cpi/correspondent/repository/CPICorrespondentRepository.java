package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.repository.other.YearCountStatistics;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CPICorrespondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPICorrespondentRepository extends JpaRepository<CPICorrespondent, Long>, JpaSpecificationExecutor<CPICorrespondent> {

    @Query("SELECT " +
        "    new com.cpi.correspondent.repository.other.YearCountStatistics(v.year, count(v)) " +
        "FROM " +
        "    CPICorrespondent v " +
        "GROUP BY " +
        "    v.year")
    List<YearCountStatistics> findYearStatsCount();

}
