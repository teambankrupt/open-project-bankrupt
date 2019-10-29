package com.example.webservice.domains.users.repositories;

import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.domains.common.models.entities.pojo.DateCountPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    Page<User> findByIdIn(List<Long> ids, Pageable pageable);

    Page<User> findByRolesName(String role, Pageable pageable);

    List<User> findByRolesName(String role);

    Page<User> findByUsernameContaining(String query, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:query% OR u.username LIKE %:query%")
    Page<User> searchByNameOrUsername(@Param("query") String query, Pageable pageable);

    Long countByCreatedBetween(Date fromDate, Date toDate);

    @Query("SELECT new com.example.webservice.domains.common.models.entities.pojo.DateCountPair(u.created, count(u.id)) FROM User u WHERE u.created BETWEEN :fromDate AND :toDate")
    List<DateCountPair> countUsersInAPeriod(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
