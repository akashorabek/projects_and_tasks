package com.forum.util;

import com.forum.model.*;
import com.forum.repository.ProjectRepository;
import com.forum.repository.TaskRepository;
import com.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Configuration
@AllArgsConstructor
public class initDB {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Bean
    public CommandLineRunner init() {
        try {
            initUsers().run();
            initProjects().run();
            initTasks().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private CommandLineRunner initUsers() {
        return (args -> Stream.of(users())
                .peek(System.out::println)
                .forEach(userRepository::save));
    }

    private CommandLineRunner initProjects() {
        return (args -> Stream.of(projects())
                .peek(System.out::println)
                .forEach(projectRepository::save));
    }

    private CommandLineRunner initTasks() {
        return (args -> Stream.of(tasks())
                .peek(System.out::println)
                .forEach(taskRepository::save));
    }

    private User[] users() {
        return new User[] {
                User.builder()
                        .id(1)
                        .email("aka.shorabek01@gmail.com")
                        .fullName("Akarys Shorabek")
                        .password("$2a$12$i.yNQsADuDtkFIc/fzzCDugkWOBtqTP/MLl9dq22HrurJs8hFsKz6")
                        .role("ROLE_USER")
                        .enabled(true)
                        .build()
        };
    }

    private Project[] projects() {
        return new Project[] {
                Project.builder()
                        .id(1)
                        .name("Project 1")
                        .description("Project 1 description")
                        .createdAt(LocalDate.parse("2022-01-30"))
                        .completedAt(LocalDate.parse("2022-04-22"))
                        .status(ProjectStatus.NOT_STARTED)
                        .priority(1)
                        .user(userRepository.getById(1))
                        .build(),
                Project.builder()
                        .id(2)
                        .name("Project 2")
                        .description("Project 2 description")
                        .createdAt(LocalDate.parse("2021-08-18"))
                        .completedAt(LocalDate.parse("2022-04-10"))
                        .status(ProjectStatus.ACTIVE)
                        .priority(4)
                        .user(userRepository.getById(1))
                        .build(),
                Project.builder()
                        .id(3)
                        .name("Project 3")
                        .description("Project 3 description")
                        .createdAt(LocalDate.parse("2021-04-15"))
                        .completedAt(LocalDate.parse("2022-04-15"))
                        .status(ProjectStatus.COMPLETED)
                        .priority(5)
                        .user(userRepository.getById(1))
                        .build(),
                Project.builder()
                        .id(4)
                        .name("Project 4")
                        .description("Project 4 description")
                        .createdAt(LocalDate.parse("2020-02-21"))
                        .completedAt(LocalDate.parse("2022-04-20"))
                        .status(ProjectStatus.NOT_STARTED)
                        .priority(2)
                        .user(userRepository.getById(1))
                        .build(),
                Project.builder()
                        .id(5)
                        .name("Some project")
                        .description("Mock Project description")
                        .createdAt(LocalDate.parse("2020-02-15"))
                        .completedAt(LocalDate.parse("2022-06-03"))
                        .status(ProjectStatus.ACTIVE)
                        .priority(2)
                        .user(userRepository.getById(1))
                        .build(),
                Project.builder()
                        .id(6)
                        .name("Building project")
                        .description("Building project description")
                        .createdAt(LocalDate.parse("2020-02-13"))
                        .completedAt(LocalDate.parse("2022-08-10"))
                        .status(ProjectStatus.ACTIVE)
                        .priority(2)
                        .user(userRepository.getById(1))
                        .build()
        };
    }

    private Task[] tasks() {
        return new Task[] {
                Task.builder()
                        .id(1)
                        .name("My task")
                        .description("My task description")
                        .status(TaskStatus.TO_DO)
                        .priority(1)
                        .user(userRepository.getById(1))
                        .project(projectRepository.getById(1))
                        .createdAt(LocalDateTime.now())
                        .build(),
                Task.builder()
                        .id(2)
                        .name("My task 2")
                        .description("My task 2 description")
                        .status(TaskStatus.DONE)
                        .priority(5)
                        .user(userRepository.getById(1))
                        .project(projectRepository.getById(1))
                        .createdAt(LocalDateTime.now())
                        .build(),
                Task.builder()
                        .id(3)
                        .name("My 3 task")
                        .description("My task 3 description")
                        .status(TaskStatus.TO_DO)
                        .priority(1)
                        .user(userRepository.getById(1))
                        .project(projectRepository.getById(1))
                        .createdAt(LocalDateTime.now())
                        .build(),
                Task.builder()
                        .id(4)
                        .name("My 4 task")
                        .description("My task 4 description")
                        .status(TaskStatus.DONE)
                        .priority(1)
                        .user(userRepository.getById(1))
                        .project(projectRepository.getById(1))
                        .createdAt(LocalDateTime.now())
                        .build(),
                Task.builder()
                        .id(5)
                        .name("My 5 task")
                        .description("My task 5 description")
                        .status(TaskStatus.IN_PROGRESS)
                        .priority(3)
                        .user(userRepository.getById(1))
                        .project(projectRepository.getById(2))
                        .createdAt(LocalDateTime.now())
                        .build()
        };
    }
}
