package ru.otus.diasoft.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.diasoft.model.Question;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

class DiagnosticsServiceTest {

    @Test
    void runTest() {
        Question question = new Question();
        question.setQuestion("2 + 2 = ?");
        question.setCorrectAnswer(1);
        question.setAnswerOption(Map.of(1, "4", 2, "8", 3, "2", 4, "12"));

        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        QuestionService questionService = Mockito.mock(QuestionService.class);
        when(questionService.getQuestions()).thenReturn(List.of(question));

        DiagnosticsService diagnosticsService = new DiagnosticsServiceImpl(questionService);

        Assertions.assertDoesNotThrow(() -> diagnosticsService.runDiagnostics());
    }
}