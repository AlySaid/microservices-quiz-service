package com.eldalashy.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int numQ;
    private String title;
    @ElementCollection
    private List<Integer> questionIds;
}

