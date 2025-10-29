package ru.otus.diasoft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.diasoft.model.Question;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static ru.otus.diasoft.constants.MessageCodes.*;

/**
 * Сервис для проведения тестирования (диагностики)
 */
@Service
public class DiagnosticsServiceImpl implements DiagnosticsService {

    private final QuestionService questionService;
    private final Integer requiredNumberCorrectAnswers;
    private final MessageSource messageSource;

    public DiagnosticsServiceImpl(@Value("${questions.answers.correct.required.number}") Integer requiredNumberCorrectAnswers,
                                  QuestionService questionService,
                                  MessageSource messageSource) {
        this.requiredNumberCorrectAnswers = requiredNumberCorrectAnswers;
        this.questionService = questionService;
        this.messageSource = messageSource;
    }

    /**
     * Запуск тестирования
     */
    public void runDiagnostics(Scanner scanner) {
        List<Question> questions = questionService.getQuestions();
        int numberCorrectAnswers = 0;

        System.out.println(messageSource.getMessage(INPUT_FIO, new Object[]{}, Locale.getDefault()));
        String name = scanner.nextLine();

        for (Question question : questions) {
            System.out.println(question.getQuestion());
            for (Map.Entry<Integer, String> answerOption : question.getAnswerOption().entrySet()) {
                System.out.println(String.format("%s - %s", answerOption.getKey(), answerOption.getValue()));
            }
            Integer answer = scanner.nextInt();
            if (question.getCorrectAnswer().equals(answer)) {
                numberCorrectAnswers++;
            }
        }

        printDiagnosticResult(numberCorrectAnswers, name);
    }

    /**
     * Вывод в консоль результатов тестирования
     *
     * @param numberCorrectAnswers количество правильных ответов
     * @param name                 имя пользователя
     */
    private void printDiagnosticResult(int numberCorrectAnswers, String name) {
        boolean diagnosticResult = this.getDiagnosticResult(numberCorrectAnswers);
        String result = diagnosticResult ? RESULT_PASSED : RESULT_NOT_PASSED;
        result = messageSource.getMessage(result, new Object[]{}, Locale.getDefault());
        System.out.println(messageSource.getMessage(RESULT,
                new String[]{name, result, String.valueOf(numberCorrectAnswers), String.valueOf(requiredNumberCorrectAnswers)},
                Locale.getDefault()));
    }

    /**
     * Получение результатов тестирования
     *
     * @param numberCorrectAnswers количество правильных ответов
     * @return результат тестирования
     */
    private boolean getDiagnosticResult(int numberCorrectAnswers) {
        return numberCorrectAnswers >= requiredNumberCorrectAnswers;
    }

}