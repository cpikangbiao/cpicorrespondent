package com.cpi.correspondent.service;


import com.cpi.correspondent.domain.*;
import com.cpi.correspondent.repository.CPICorrespondentRepository;
import com.cpi.correspondent.repository.other.MonthCountStatistics;
import com.cpi.correspondent.repository.other.TypeCountStatistics;
import com.cpi.correspondent.repository.other.YearCountStatistics;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.mapper.CPICorrespondentMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service for executing complex queries for CPICorrespondent entities in the database.
 * The main input is a {@link CPICorrespondentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPICorrespondentDTO} or a {@link Page} of {@link CPICorrespondentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPICorrespondentStatisticsService extends QueryService<CPICorrespondent> {

    private final Logger log = LoggerFactory.getLogger(CPICorrespondentStatisticsService.class);


    private final CPICorrespondentRepository cPICorrespondentRepository;

    private final CPICorrespondentMapper cPICorrespondentMapper;

    public CPICorrespondentStatisticsService(CPICorrespondentRepository cPICorrespondentRepository, CPICorrespondentMapper cPICorrespondentMapper) {
        this.cPICorrespondentRepository = cPICorrespondentRepository;
        this.cPICorrespondentMapper = cPICorrespondentMapper;
    }


    @Transactional(readOnly = true)
    public List<YearCountStatistics> findYearCountStatistics() {
        log.debug("find by YearCountStatistics ");
        return cPICorrespondentRepository.findYearStatsCount();
    }

    @Transactional(readOnly = true)
    public List<MonthCountStatistics> findMonthCountStatistics() {
        log.debug("find by findMonthCountStatistics ");
        Instant startDate = ZonedDateTime.now().minusYears(1).toInstant();
        Instant endDate = Instant.now();
        return cPICorrespondentRepository.findMonthStatsCount(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<TypeCountStatistics> findTypeCountStatistics() {
        log.debug("find by findTypeCountStatistics ");
        return cPICorrespondentRepository.findTypeStatsCount();
    }

    @Transactional(readOnly = true)
    public List<TypeCountStatistics> findClubCountStatistics() {
        log.debug("find by findTypeCountStatistics ");
        return cPICorrespondentRepository.findClubStatsCount();
    }
}
