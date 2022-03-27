package com.forum.controller.frontendController;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ProjectController {

    @GetMapping
    public String root() {
        return "index";
    }

    @GetMapping("/projects/{id}")
    public String viewProject(@PathVariable int id, Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("userEmail", authentication.getName());
        }
        model.addAttribute("id", id);
        return "project_item";
    }

    @GetMapping("/projects/create")
    public String viewCreateProject() {
        return "create_project";
    }
}
