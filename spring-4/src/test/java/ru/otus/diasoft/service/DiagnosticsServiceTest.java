package ru.otus.diasoft.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.diasoft.constants.Locales;
import ru.otus.diasoft.model.Question;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.otus.diasoft.constants.MessageCodes.*;

@DisplayName("Сервис диагностики")
@ExtendWith(MockitoExtension.class)
class DiagnosticsServiceTest {

    @Mock
    private QuestionService questionService;
    @Mock
    private MessageSource messageSource;

    @Test
    @DisplayName("Вывод в консоль, когда тест пройден")
    void consoleOutputWhenTestPassed() {
        Question question = new Question();
        question.setLocale("ru_RU");
        question.setQuestion("2 + 2 = ?");
        question.setCorrectAnswer(1);
        question.setAnswerOption(Map.of(1, "4", 2, "8", 3, "2", 4, "12"));

        String input = "Ivan Ivanovich\n" +
                "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Object[] params = {"Ivan Ivanovich", "пройден", "1", "1"};

        when(questionService.getQuestions()).thenReturn(List.of(question));
        when(messageSource.getMessage(INPUT_FIO, new Object[]{}, Locale.forLanguageTag("ru-RU"))).thenReturn("Введите ваше имя и фамилию");
        when(messageSource.getMessage(RESULT_PASSED, new Object[]{}, Locale.forLanguageTag("ru-RU"))).thenReturn("пройден");
        when(messageSource.getMessage(RESULT, params, Locale.forLanguageTag("ru-RU")))
                .thenReturn("Результаты тестирования пользователя Ivan Ivanovich. Тест пройден. Правильных ответов 1 из необходимых 1.");

        DiagnosticsService diagnosticsService = new DiagnosticsServiceImpl(1, questionService, messageSource);

        Locale.setDefault(Locale.forLanguageTag(Locales.RU));
        try (Scanner scanner = new Scanner(in)) {
            diagnosticsService.runDiagnostics(scanner);
            assertThat(outputStream.toString()).contains("Тест пройден");
        }
    }

    @Test
    @DisplayName("Вывод в консоль, когда тест не пройден")
    void consoleOutputWhenTestFail() {
        Question question1 = new Question();
        question1.setLocale("ru_RU");
        question1.setQuestion("2 + 2 = ?");
        question1.setCorrectAnswer(1);
        question1.setAnswerOption(Map.of(1, "4", 2, "8", 3, "2", 4, "12"));

        Question question2 = new Question();
        question2.setLocale("ru_RU");
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

        Object[] params = {"Ivan Ivanovich", "не пройден", "1", "2"};

        when(questionService.getQuestions()).thenReturn(List.of(question1, question2));
        when(messageSource.getMessage(INPUT_FIO, new Object[]{}, Locale.forLanguageTag("ru-RU"))).thenReturn("Введите ваше имя и фамилию");
        when(messageSource.getMessage(RESULT_NOT_PASSED, new Object[]{}, Locale.forLanguageTag("ru-RU"))).thenReturn("не пройден");
        when(messageSource.getMessage(RESULT, params, Locale.forLanguageTag("ru-RU")))
                .thenReturn("Результаты тестирования пользователя Ivan Ivanovich. Тест не пройден. Правильных ответов 1 из необходимых 2.");

        DiagnosticsService diagnosticsService = new DiagnosticsServiceImpl(2, questionService, messageSource);

        Locale.setDefault(Locale.forLanguageTag(Locales.RU));
        try (Scanner scanner = new Scanner(in)) {
            diagnosticsService.runDiagnostics(scanner);
            assertThat(outputStream.toString()).contains("Тест не пройден");
        }
    }
}