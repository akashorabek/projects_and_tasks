package com.forum.controller.frontendController;

import com.forum.model.DTO.RegisterUserDTO;
import com.forum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }

    @GetMapping("/login-error")
    public String viewErrorLogin(Model model) {
        model.addAttribute("hasError", true);
        return "login";
    }

    @GetMapping("/register")
    public String viewRegister() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String register(@Valid RegisterUserDTO userDTO, Model model) {
        if (!service.register(userDTO)) {
            model.addAttribute("hasServiceErrors", "Пользователь с такой почтой уже есть.");
            return "register";
        }
        return "redirect:/";
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected String handleRegisterBind(BindException ex, Model model) {
        var errors = ex.getFieldErrors()
                .stream()
                .map(fe -> fe.getDefaultMessage())
                .collect(Collectors.toList());
        model.addAttribute("bindErrors", errors);
        return "register";
    }
}
