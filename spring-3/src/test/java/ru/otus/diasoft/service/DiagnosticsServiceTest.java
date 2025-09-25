package ru.otus.diasoft.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.diasoft.model.Question;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис диагностики")
class DiagnosticsServiceTest {

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

        DiagnosticsService diagnosticsService = new DiagnosticsService(1);

        diagnosticsService.runTest(List.of(question));
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

        DiagnosticsService diagnosticsService = new DiagnosticsService(2);

        diagnosticsService.runTest(List.of(question1, question2));
        assertThat(outputStream.toString()).contains("Тест не пройден");
    }
}