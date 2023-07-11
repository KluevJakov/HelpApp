package com.HelpApp.service;

import com.HelpApp.entity.Feedback;
import com.HelpApp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback create(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> list() {
        return feedbackRepository.findAll();
    }
}
