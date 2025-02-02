package com.eldalashy.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class QuizDTO {
    String category;
    Integer numberOfQuestions;
    String title;

}
