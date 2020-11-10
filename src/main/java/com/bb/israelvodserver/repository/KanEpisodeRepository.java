package com.bb.israelvodserver.repository;

import com.bb.israelvodserver.domain.KanEpisode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KanEpisode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KanEpisodeRepository extends JpaRepository<KanEpisode, Long> {
}
