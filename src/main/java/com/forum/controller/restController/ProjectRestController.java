package com.forum.controller.restController;

import com.forum.exception.ProjectNotFoundException;
import com.forum.model.dto.ProjectAddDto;
import com.forum.model.dto.ProjectDto;
import com.forum.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ProjectRestController {
    private final ProjectService service;

    @GetMapping("/api/projects")
    public Page<ProjectDto> getProjects(@RequestParam(defaultValue = "0") int page) {
        return service.findAllSortedByDate((int)page);
    }

    @GetMapping("/api/projects/search")
    public Page<ProjectDto> getSearchedProjects(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "") String query) {
        return service.findAllSearchedByQuery((int)page, query);
    }

    @GetMapping("/api/projects/{id}")
    public ProjectDto getProjectItem(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping("/api/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@Valid ProjectAddDto projectAddDto, Authentication authentication) {
        service.create(projectAddDto, authentication.getName());
    }

    @PutMapping("/api/projects")
    @ResponseStatus(HttpStatus.OK)
    public void editProject(@Valid ProjectAddDto projectAddDto) {
        service.edit(projectAddDto);
    }

    @DeleteMapping("/api/projects")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@RequestParam int projectId) {
        service.delete(projectId);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ex.getMessage();
    }
}
