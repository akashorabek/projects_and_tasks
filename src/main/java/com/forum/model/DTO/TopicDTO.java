package com.forum.model.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TopicDTO {
    @Size(min = 5, max = 250, message = "Название темы должно состоять от 5 до 250 символов")
    @NotBlank(message = "Название темы не может быть пустой")
    private String name;

    @Size(min = 10, max = 250, message = "Описание темы должно состоять от 10 до 250 символов")
    @NotBlank(message = "Описание темы не может быть пустой")
    private String description;
}
