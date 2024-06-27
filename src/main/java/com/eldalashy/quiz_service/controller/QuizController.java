package com.eldalashy.quiz_service.controller;

import com.eldalashy.quiz_service.model.QuestionWrapper;
import com.eldalashy.quiz_service.model.QuizDTO;
import com.eldalashy.quiz_service.model.Response;
import com.eldalashy.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    //    http://localhost:8080/quiz/create?cat=Java&numQ=5&title=JavaQuiz
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
        return quizService.createQuiz(quizDTO.getCategory(), quizDTO.getNumberOfQuestions(), quizDTO.getTitle());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("id") Integer id, @RequestBody List<Response> responses) {
        ResponseEntity<Integer> score = quizService.calculateScore(id, responses);
        return score;
    }
}
