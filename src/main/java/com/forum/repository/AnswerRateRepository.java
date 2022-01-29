package com.forum.repository;

import com.forum.model.AnswerRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRateRepository extends JpaRepository<AnswerRate, Integer> {
    AnswerRate getByAnswerIdAndUserId(int answerId, int userId);
}
