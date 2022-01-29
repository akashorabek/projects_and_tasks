package com.forum.controller.frontendController;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class TopicController {

    @GetMapping
    public String root() {
        return "index";
    }

    @GetMapping("/topics/{id}")
    public String viewTopic(@PathVariable int id, Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("userEmail", authentication.getName());
        }
        model.addAttribute("id", id);
        return "topic_item";
    }

    @GetMapping("/create_topic")
    public String viewCreateTopic() {
        return "create_topic";
    }
}
