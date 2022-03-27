package com.forum.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterUserDto {
    @Email(message = "Email must be in mail format.")
    @Size(min = 4, max = 100, message = "Email must be between 4 and 100 characters")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
