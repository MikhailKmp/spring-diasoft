package ru.otus.diasoft.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис вопросов")
class QuestionServiceTest {

    @Test
    @DisplayName("Конструктор создает вопросы из файла")
    void constructorShouldCreateQuestions() throws IOException {
        QuestionService questionService = new QuestionServiceImpl("src/test/resources/questions.csv", 3);
        assertThat(questionService.getQuestions()).isNotEmpty();
    }

    @Test
    @DisplayName("Конструктор выдает ошибку, если неверный формат вопросов")
    void constructorShouldThrowExceptionWhenFormatIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new QuestionServiceImpl("src/test/resources/questionsError.csv", 3));
    }

}