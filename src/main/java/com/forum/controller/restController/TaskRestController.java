package com.forum.controller.restController;

import com.forum.model.dto.TaskDto;
import com.forum.model.dto.TaskAddDto;
import com.forum.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// Rest controller for task services
@RestController
@AllArgsConstructor
public class TaskRestController {
    private TaskService service;

    @GetMapping("/api/tasks/{projectId}")
    public Page<TaskDto> getTasksByProjectId(@PathVariable int projectId, @RequestParam(defaultValue = "0") int page) {
        return service.findTasksByProjectId(projectId, page);
    }

    @PostMapping("/api/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid TaskAddDto taskAddDto, Authentication authentication) {
        if(authentication != null) {
            service.create(taskAddDto, authentication.getName());
        }
    }

    @PostMapping("/api/tasks/rates/{rateType}")
    public void rateTask(@RequestParam int taskId, Authentication authentication, @PathVariable String rateType) {
        if (authentication != null) {
            service.rate(taskId, authentication.getName(), rateType);
        }
    }

    @PutMapping("/api/tasks")
    @ResponseStatus(HttpStatus.OK)
    public void editTask(@Valid TaskAddDto taskAddDto) {
        service.edit(taskAddDto);
    }

    @DeleteMapping("/api/tasks")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@RequestParam int taskId) {
        service.delete(taskId);
    }
}
