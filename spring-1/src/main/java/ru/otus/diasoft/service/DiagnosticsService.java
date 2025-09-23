package ru.otus.diasoft.service;

import ru.otus.diasoft.model.Question;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Сервис для проведения тестирования (диагностики)
 */
public class DiagnosticsService {

    /**
     * Запуск тестирования
     */
    public static void runTest(List<Question> questions) {
        int numberCorrectAnswers = 0;
        try (Scanner scanner = new Scanner(System.in)) {
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
        }
        System.out.println(String.format("Количество правильных ответов: %s / %s", numberCorrectAnswers, questions.size()));
    }

}
