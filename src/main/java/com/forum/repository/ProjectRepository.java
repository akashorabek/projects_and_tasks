package com.forum.repository;

import com.forum.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Page<Project> findAll(Pageable pageable);


    // Search by matching name or description
    @Query(
            "select t from Project as t where (" +
            "lower(t.name) like CONCAT('%' || lower(:query) || '%') or " +
            "lower(t.description) like CONCAT('%' || lower(:query) || '%'))"
          )
    Page<Project> findAllByQuery(String query, Pageable pageable);
}
