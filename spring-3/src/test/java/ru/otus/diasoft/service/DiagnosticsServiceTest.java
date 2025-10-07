package ru.otus.diasoft.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.diasoft.model.Question;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Сервис диагностики")
@ExtendWith(MockitoExtension.class)
class DiagnosticsServiceTest {

    @Mock
    private QuestionService questionService;

    @Test
    @DisplayName("Вывод в консоль, когда тест пройден")
    void consoleOutputWhenTestPassed() {
        Question question = new Question();
        question.setQuestion("2 + 2 = ?");
        question.setCorrectAnswer(1);
        question.setAnswerOption(Map.of(1, "4", 2, "8", 3, "2", 4, "12"));

        String input = "Ivan Ivanovich\n" +
                "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        when(questionService.getQuestions()).thenReturn(List.of(question));
        DiagnosticsService diagnosticsService = new DiagnosticsServiceImpl(1, questionService);
        diagnosticsService.runDiagnostics();
        assertThat(outputStream.toString()).contains("Тест пройден");
    }

    @Test
    @DisplayName("Вывод в консоль, когда тест не пройден")
    void consoleOutputWhenTestFail() {
        Question question1 = new Question();
        question1.setQuestion("2 + 2 = ?");
        question1.setCorrectAnswer(1);
        question1.setAnswerOption(Map.of(1, "4", 2, "8", 3, "2", 4, "12"));

        Question question2 = new Question();
        question2.setQuestion("3 + 3 = ?");
        question2.setCorrectAnswer(1);
        question2.setAnswerOption(Map.of(1, "6", 2, "9", 3, "12", 4, "27"));

        String input = "Ivan Ivanovich\n" +
                "1\n" +
                "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        when(questionService.getQuestions()).thenReturn(List.of(question1, question2));
        DiagnosticsService diagnosticsService = new DiagnosticsServiceImpl(2, questionService);
        diagnosticsService.runDiagnostics();
        assertThat(outputStream.toString()).contains("Тест не пройден");
    }
}