package com.forum.controller.restController;

import com.forum.exception.TopicNotFoundException;
import com.forum.model.DTO.TopicDTO;
import com.forum.model.Topic;
import com.forum.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class TopicRestController {
    private final TopicService service;

    @GetMapping("/topics")
    public Page<Topic> getTopics(@RequestParam(defaultValue = "0") int page) {
        return service.findAllSortedByDate((int)page);
    }

    @GetMapping("/topics/get/{id}")
    public Topic getTopicItem(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping("/topics")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTopic(@Valid TopicDTO topicDTO, Authentication authentication) {
        service.create(topicDTO, authentication.getName());
    }

    @ExceptionHandler(TopicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String handleTopicNotFoundException(TopicNotFoundException ex) {
        return ex.getMessage();
    }
}
