package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.repository.other.MonthCountStatistics;
import com.cpi.correspondent.repository.other.TypeCountStatistics;
import com.cpi.correspondent.repository.other.YearCountStatistics;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the CPICorrespondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPICorrespondentRepository extends JpaRepository<CPICorrespondent, Long>, JpaSpecificationExecutor<CPICorrespondent> {

    @Query("SELECT " +
        "    new com.cpi.correspondent.repository.other.YearCountStatistics(v.year, count(v)) " +
        " FROM " +
        "    CPICorrespondent v " +
        " GROUP BY " +
        "    v.year")
    List<YearCountStatistics> findYearStatsCount();

    @Query(value = "SELECT "
        + " new com.cpi.correspondent.repository.other.MonthCountStatistics( DATE_FORMAT(v.caseDate, '%Y-%m'), COUNT(v) ) "
        + " FROM CPICorrespondent v"
        + " WHERE v.caseDate BETWEEN :startDate AND :endDate"
        + " GROUP BY DATE_FORMAT(v.caseDate, '%Y-%m')")
    List<MonthCountStatistics> findMonthStatsCount(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query(value = "SELECT "
        + " new com.cpi.correspondent.repository.other.TypeCountStatistics( t.correspondentTypeName, COUNT(v) ) "
        + " FROM CPICorrespondent v, CorrespondentType t"
        + " WHERE v.correspondentType.id = t.id"
        + " GROUP BY v.correspondentType.correspondentTypeName ")
    List<TypeCountStatistics> findTypeStatsCount();

    @Query(value = "SELECT "
        + " new com.cpi.correspondent.repository.other.TypeCountStatistics( t.clubName, COUNT(v) ) "
        + " FROM CPICorrespondent v, Club t"
        + " WHERE v.club.id = t.id"
        + " GROUP BY v.club.clubName ")
    List<TypeCountStatistics> findClubStatsCount();

}
