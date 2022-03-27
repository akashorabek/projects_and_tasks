package com.forum.model.dto;

import com.forum.model.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ProjectDto {
    private int id;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate completedAt;
    private String status;
    private int priority;
    private String username;
    List<Integer> tasks;

    public static ProjectDto from(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .completedAt(project.getCompletedAt())
                .status(project.getStatus().getLabel())
                .priority(project.getPriority())
                .username(project.getUser().getFullName())
                .tasks(project.getTasks().stream()
                        .map(t -> t.getId())
                        .collect(Collectors.toList()))
                .build();
    }
}
