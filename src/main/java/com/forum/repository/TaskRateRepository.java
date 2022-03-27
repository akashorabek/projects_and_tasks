package com.forum.repository;

import com.forum.model.TaskRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRateRepository extends JpaRepository<TaskRate, Integer> {
    TaskRate getByTaskIdAndUserId(int answerId, int userId);
}
