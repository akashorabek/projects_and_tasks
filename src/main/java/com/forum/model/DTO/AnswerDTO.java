package com.forum.model.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AnswerDTO {
    @Size(min = 1, max = 250, message = "Ответ должен состоять от 1 до 250 символов")
    @NotBlank(message = "Ответ не может быть пустым")
    private String message;

    private int topicId;
}
