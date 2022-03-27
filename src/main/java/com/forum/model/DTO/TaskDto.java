package com.forum.model.DTO;

import com.forum.model.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class TaskDto {
    private int id;
    private String name;
    private String description;
    private String status;
    private int priority;
    private LocalDateTime createdAt;
    private String username;
    private String userEmail;
    private List<TaskRateDto> taskRates;

    public static TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus().getLabel())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .username(task.getUser().getFullName())
                .userEmail(task.getUser().getEmail())
                .taskRates(task.getTaskRates().stream()
                        .map(t -> TaskRateDto.from(t))
                        .collect(Collectors.toList()))
                .build();
    }
}
