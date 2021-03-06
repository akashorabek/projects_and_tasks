package com.forum.controller.frontendController;

import com.forum.model.dto.RegisterUserDto;
import com.forum.model.User;
import com.forum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
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
    public String register(@Valid RegisterUserDto userDTO, Model model) throws IOException {
        if (!service.register(userDTO)) {
            model.addAttribute("hasServiceErrors", "User already exists!");
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/cabinet")
    public String viewCabinet(Model model, Authentication authentication) {
        User user = service.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "cabinet";
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected String handleRegisterBind(BindException ex, Model model) {
        List errors = ex.getFieldErrors()
                .stream()
                .map(fe -> fe.getDefaultMessage())
                .collect(Collectors.toList());
        model.addAttribute("bindErrors", errors);
        return "register";
    }
}
