package com.forum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter @Setter
@AllArgsConstructor
public class TopicNotFoundException extends RuntimeException {
    private String message;
}
