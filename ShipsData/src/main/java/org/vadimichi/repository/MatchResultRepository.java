package org.vadimichi.repository;

import org.vadimichi.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchResultRepository extends JpaRepository<MatchResult,Long> {
}
