package com.forum.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProjectAddDto {
    private int id;

    @Size(min = 1, max = 250, message = "Name must be between 1 and 250 characters")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(min = 1, max = 250, message = "Description must be between 1 and 250 characters")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Completion cannot be empty")
    private String completedAt;

    @Max(value = 5, message = "Priority's max value is 5")
    @Min(value = 1, message = "Priority's min value is 1")
    private int priority;

    private String status;
}
