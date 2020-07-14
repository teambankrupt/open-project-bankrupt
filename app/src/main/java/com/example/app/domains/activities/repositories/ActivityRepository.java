package com.example.app.domains.activities.repositories;

import com.example.app.domains.activities.models.entities.Activity;
import com.example.auth.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findFirstBy();

    Activity findFirstByUserOrderByIdDesc(User user);

    Page<Activity> findByUser(User user, Pageable pageable);
}
