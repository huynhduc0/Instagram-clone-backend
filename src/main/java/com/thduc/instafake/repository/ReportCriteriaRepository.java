package com.thduc.instafake.repository;

import com.thduc.instafake.entity.ReportCriterias;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCriteriaRepository extends PagingAndSortingRepository<ReportCriterias,Long> {
    boolean existsByCriteriaName(String crit);
}
