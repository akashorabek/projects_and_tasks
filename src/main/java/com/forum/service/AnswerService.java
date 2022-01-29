package com.forum.service;

import com.forum.model.Answer;
import com.forum.model.AnswerRate;
import com.forum.model.DTO.AnswerDTO;
import com.forum.model.Topic;
import com.forum.model.User;
import com.forum.repository.AnswerRateRepository;
import com.forum.repository.AnswerRepository;
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
public class AnswerService {
    private final AnswerRepository repository;
    private final AnswerRateRepository answerRateRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public Page<Answer> findAnswersByTopicId(int topicId, int pageNumber) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        return repository.findAllByTopicId(topicId, pageable);
    }

    public void create(AnswerDTO answerDTO, String userEmail) {
        Answer answer = new Answer();
        answer.setMessage(answerDTO.getMessage());
        User user = userRepository.findByEmail(userEmail);
        Topic topic = topicRepository.getById(answerDTO.getTopicId());
        LocalDateTime currentDate = LocalDateTime.now();
        answer.setUser(user);
        answer.setTopic(topic);
        answer.setCreatedAt(currentDate);
        repository.save(answer);
    }

    public void rate(int answerId, String userEmail, String rateType) {
        User user = userRepository.findByEmail(userEmail);
        AnswerRate answerRate = answerRateRepository.getByAnswerIdAndUserId(answerId, user.getId());
        Answer answer = repository.getById(answerId);

        if (answerRate != null || answer.getUser().getEmail().equals(userEmail)) {
            return;
        }

        answerRate = new AnswerRate();
        answerRate.setUser(user);
        answerRate.setAnswer(answer);

        if (rateType.equals("liked")) {
            answerRate.setLiked(true);
            answerRate.setDisliked(false);
        } else {
            answerRate.setLiked(false);
            answerRate.setDisliked(true);
        }

        answerRateRepository.save(answerRate);
    }
}
