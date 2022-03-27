package com.forum.service;

import com.forum.exception.ProjectNotFoundException;
import com.forum.model.DTO.ProjectAddDto;
import com.forum.model.DTO.ProjectDto;
import com.forum.model.Project;
import com.forum.model.ProjectStatus;
import com.forum.model.User;
import com.forum.repository.ProjectRepository;
import com.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final UserRepository userRepository;

    public Page<ProjectDto> findAllSortedByDate(int pageNumber) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Project> projects = repository.findAll(pageable);
        return new PageImpl<ProjectDto>(
                projects.getContent().stream()
                        .map(ProjectDto::from)
                        .collect(Collectors.toList()),
                pageable, projects.getTotalElements()
        );
    }

    public Page<ProjectDto> findAllSearchedByQuery(int pageNumber, String query) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Project> projects = repository.findAllByQuery(query, pageable);
        return new PageImpl<ProjectDto>(
                projects.getContent().stream()
                        .map(ProjectDto::from)
                        .collect(Collectors.toList()),
                pageable, projects.getTotalElements()
        );
    }

    public ProjectDto findById(int id) {
        Project project = repository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        return ProjectDto.from(project);
    }

    public void create(ProjectAddDto projectAddDto, String userEmail) {
        LocalDate createdAt = LocalDate.now();
        LocalDate completedAt = LocalDate.parse(projectAddDto.getCompletedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        User user = userRepository.findByEmail(userEmail);

        Project project = Project.builder()
                .name(projectAddDto.getName())
                .description(projectAddDto.getDescription())
                .createdAt(createdAt)
                .completedAt(completedAt)
                .priority(projectAddDto.getPriority())
                .status(ProjectStatus.NOT_STARTED)
                .user(user)
                .build();
        repository.save(project);
    }

    public void edit(ProjectAddDto projectAddDto) {
        Project project = repository.getById(projectAddDto.getId());
        project.setName(projectAddDto.getName());
        project.setStatus(ProjectStatus.valueOfLabel(projectAddDto.getStatus()));
        project.setDescription(projectAddDto.getDescription());
        project.setCompletedAt(LocalDate.parse(projectAddDto.getCompletedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        project.setPriority(projectAddDto.getPriority());
        repository.save(project);
    }

    public void delete(int projectId) {
        repository.deleteById(projectId);
    }
}
