package com.forum.controller.restController;

import com.forum.model.Answer;
import com.forum.model.DTO.AnswerDTO;
import com.forum.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AnswerRestController {
    private AnswerService service;

    @GetMapping("/answers/{topicId}")
    public Page<Answer> getAnswersByTopicId(@PathVariable int topicId, @RequestParam(defaultValue = "0") int page) {
        return service.findAnswersByTopicId(topicId, page);
    }

    @PostMapping("/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnswer(@Valid AnswerDTO answerDTO, Authentication authentication) {
        service.create(answerDTO, authentication.getName());
    }

    @PostMapping("/answers/rates/{rateType}")
    public void rateAnswer(@RequestParam int answerId, Authentication authentication, @PathVariable String rateType) {
        if (authentication != null) {
            service.rate(answerId, authentication.getName(), rateType);
        }
    }
}
