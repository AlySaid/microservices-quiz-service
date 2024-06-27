package com.eldalashy.quiz_service.service;

import com.eldalashy.quiz_service.dao.QuizDao;
import com.eldalashy.quiz_service.feign.QuestionServiceFeignClient;
import com.eldalashy.quiz_service.model.QuestionWrapper;
import com.eldalashy.quiz_service.model.Quiz;
import com.eldalashy.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionServiceFeignClient questionServiceClient;

    public ResponseEntity<String> createQuiz(String cat, int numQ, String title) {
        try {
            //call generate from question service

            List<Integer> questionIds = questionServiceClient.getQuestionsForQuiz(cat, numQ).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setNumQ(numQ);
            quiz.setQuestionIds(questionIds);
            quizDao.save(quiz);

            return new ResponseEntity<>("Quiz " + title + " created !!", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error creating Quiz " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try {
            Quiz quiz = quizDao.findById(id).get();
            List<Integer> questionsIds = quiz.getQuestionIds();
            List<QuestionWrapper> questionWrappers = questionServiceClient.getQuestionFromIds(questionsIds).getBody();
            return new ResponseEntity<>(questionWrappers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> responses) {

     return  questionServiceClient.getScore(responses);

    }


//    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> responses) {
//        int score, i;
//        try {
//            Optional<Quiz> quiz = quizDao.findById(id);
//            List<Question> quizQuestions;
//            score = 0;
//            i = 0;
//            if (!quiz.isEmpty()) {
//                quizQuestions = quiz.get().getQuestions();
//                Iterator<Question> iterator = quizQuestions.iterator();
//                while (iterator.hasNext()) {
//                    Question question = iterator.next();
//                    if (question.getRightAnswer().equals(responses.get(i).getSubmitAnswer())) {
//                        score++;
//                    }
//                    i++;
//                }
//
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(0, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(score, HttpStatus.OK);
//    }
}
