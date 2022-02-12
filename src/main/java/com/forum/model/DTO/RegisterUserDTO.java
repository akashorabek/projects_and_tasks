package com.forum.model.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterUserDTO {
    @Email(message = "email должен иметь формат почты.")
    @Size(min = 4, max = 100, message = "Почта должна состоять от 4 до 100 символов")
    @NotBlank(message = "Почта не может быть пустой")
    private String email;

    @Size(min = 2, max = 100, message = "Имя должно состоять от 1 до 100 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String fullName;

    @Size(min = 6, max = 20, message = "Пароль должен состоять от 6 до 20 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotNull(message = "Картинка не может быть пустой")
    private MultipartFile image;
}
