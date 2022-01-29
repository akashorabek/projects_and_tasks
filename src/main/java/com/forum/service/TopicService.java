package com.forum.service;

import com.forum.exception.TopicNotFoundException;
import com.forum.model.DTO.TopicDTO;
import com.forum.model.Topic;
import com.forum.model.User;
import com.forum.repository.TopicRepository;
import com.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TopicService {
    private final TopicRepository repository;
    private final UserRepository userRepository;

    public Page<Topic> findAllSortedByDate(int pageNumber) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        return repository.findAll(pageable);
    }

    public Topic findById(int id) {
        return repository.findById(id).orElseThrow(() -> new TopicNotFoundException("Тема с id " + id + " не найдено"));
    }

    public void create(TopicDTO topicDTO, String userEmail) {
        Topic topic = new Topic();
        topic.setName(topicDTO.getName());
        topic.setDescription(topicDTO.getDescription());
        User user = userRepository.findByEmail(userEmail);
        topic.setUser(user);
        LocalDateTime currentDate = LocalDateTime.now();
        topic.setCreatedAt(currentDate);
        repository.save(topic);
    }
}
