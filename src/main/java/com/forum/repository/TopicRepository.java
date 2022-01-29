package com.forum.repository;

import com.forum.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Page<Topic> findAll(Pageable pageable);

    @Query(
            "select t from Topic as t where (" +
            "lower(t.name) like CONCAT('%' || lower(:query) || '%') or " +
            "lower(t.description) like CONCAT('%' || lower(:query) || '%'))"
          )
    Page<Topic> findAllByQuery(String query, Pageable pageable);
}
