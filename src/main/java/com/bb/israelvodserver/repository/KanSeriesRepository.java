package com.bb.israelvodserver.repository;

import com.bb.israelvodserver.domain.KanSeries;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KanSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KanSeriesRepository extends JpaRepository<KanSeries, Long> {
}
