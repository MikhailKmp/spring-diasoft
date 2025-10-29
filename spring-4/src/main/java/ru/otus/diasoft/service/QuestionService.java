package ru.otus.diasoft.service;

import ru.otus.diasoft.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestions();

    List<Question> getQuestions(String locale);
}