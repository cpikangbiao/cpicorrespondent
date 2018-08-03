package com.cpi.correspondent.repository;

import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.CorrespondentFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CorrespondentBill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentBillRepository extends JpaRepository<CorrespondentBill, Long>, JpaSpecificationExecutor<CorrespondentBill> {

//    Integer getMaxNumberIdByYear(String year);


    List<CorrespondentBill> findByCpiCorrespondentId(Long cpiCorrespondentId);

    CorrespondentBill findTopByYearOrderByNumberIdDesc(String year);

    Page<CorrespondentBill> findAllByBillFinanceTypeIdAAndCorrespondentBillStatusIdOrderByDueDateDesc(Long billFinanceTypeId, Long correspondentBillStatusId, Pageable pageable);
}
