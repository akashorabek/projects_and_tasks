package com.forum.service;

import com.forum.model.*;
import com.forum.model.dto.TaskAddDto;
import com.forum.model.dto.TaskDto;
import com.forum.repository.TaskRateRepository;
import com.forum.repository.TaskRepository;
import com.forum.repository.ProjectRepository;
import com.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskRateRepository taskRateRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Page<TaskDto> findTasksByProjectId(int projectId, int pageNumber) {
        // Pageable configs
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));

        Page<Task> tasks = repository.findAllByProjectId(projectId, pageable);
        return new PageImpl<TaskDto>(
                tasks.getContent().stream()
                        .map(TaskDto::from)
                        .collect(Collectors.toList()),
                pageable, tasks.getTotalElements()
        );
    }

    public void create(TaskAddDto taskAddDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Project project = projectRepository.getById(taskAddDto.getProjectId());
        LocalDateTime currentDate = LocalDateTime.now();
        Task task = Task.builder()
                .name(taskAddDto.getName())
                .description(taskAddDto.getDescription())
                .priority(taskAddDto.getPriority())
                .project(project)
                .user(user)
                .createdAt(currentDate)
                .status(TaskStatus.TO_DO)
                .build();
        repository.save(task);
    }

    // like and dislike tasks method
    public void rate(int taskId, String userEmail, String rateType) {
        User user = userRepository.findByEmail(userEmail);
        TaskRate taskRate = taskRateRepository.getByTaskIdAndUserId(taskId, user.getId());
        Task task = repository.getById(taskId);

        if (taskRate != null || task.getUser().getEmail().equals(userEmail)) {
            return;
        }

        taskRate = TaskRate.builder()
                .user(user)
                .task(task)
                .build();

        if (rateType.equals("liked")) {
            taskRate.setLiked(true);
            taskRate.setDisliked(false);
        } else {
            taskRate.setLiked(false);
            taskRate.setDisliked(true);
        }

        taskRateRepository.save(taskRate);
    }

    public void edit(TaskAddDto taskAddDto) {
        Task task = repository.getById(taskAddDto.getId());
        task.setName(taskAddDto.getName());
        task.setDescription(taskAddDto.getDescription());
        task.setStatus(TaskStatus.valueOfLabel(taskAddDto.getStatus()));
        task.setPriority(taskAddDto.getPriority());
        repository.save(task);
    }

    public void delete(int taskId) {
        repository.deleteById(taskId);
    }
}
