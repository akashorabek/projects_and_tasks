package com.forum.service;

import com.forum.exception.ProjectNotFoundException;
import com.forum.model.dto.ProjectAddDto;
import com.forum.model.dto.ProjectDto;
import com.forum.model.Project;
import com.forum.model.ProjectStatus;
import com.forum.model.User;
import com.forum.model.specification.ProjectSpecification;
import com.forum.repository.ProjectRepository;
import com.forum.repository.UserRepository;
import com.forum.util.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
        // Pageable configs
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
        // Pageable configs
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));

        // Setting up specifications to search for a project by matches
        ProjectSpecification nameLike = new ProjectSpecification(new SearchCriteria("name", ":", query));
        ProjectSpecification descriptionLike = new ProjectSpecification(new SearchCriteria("description", ":", query));
        ProjectSpecification userNameLike = new ProjectSpecification(new SearchCriteria("user.fullName", "->:", query));
        ProjectSpecification priorityLike = new ProjectSpecification(new SearchCriteria("priority", ":", query));

        Page<Project> projects = repository.findAll(Specification.where(nameLike).or(descriptionLike).or(userNameLike).or(priorityLike), pageable);
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
