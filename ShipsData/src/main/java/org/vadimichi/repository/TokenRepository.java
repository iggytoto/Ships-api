package org.vadimichi.repository;

import org.vadimichi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "SELECT t FROM Token t WHERE t.userId = :userid")
    Token findByUserId(@Param("userid") long userId);
}
