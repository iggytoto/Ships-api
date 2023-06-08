package org.vadimichi.repository;

import org.vadimichi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM " + User.TABLE_NAME + " u WHERE u." + User.LOGIN_COLUMN_NAME + " = :login", nativeQuery = true)
    User findUserByLogin(@Param("login") String login);
}
