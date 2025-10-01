package ru.otus.diasoft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.diasoft.model.Question;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Сервис для проведения тестирования (диагностики)
 */
@Service
public class DiagnosticsServiceImpl implements DiagnosticsService {

    private final QuestionService questionService;
    private final Integer requiredNumberCorrectAnswers;

    public DiagnosticsServiceImpl(@Value("${questions.answers.correct.required.number}") Integer requiredNumberCorrectAnswers,
                                  QuestionService questionService) {
        this.requiredNumberCorrectAnswers = requiredNumberCorrectAnswers;
        this.questionService = questionService;
    }

    /**
     * Запуск тестирования
     */
    public void runDiagnostics() {
        List<Question> questions = questionService.getQuestions();
        int numberCorrectAnswers = 0;
        String name;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите ваше имя и фамилию");
            name = scanner.nextLine();
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
        String result = diagnosticResult ? "пройден" : "не пройден";
        System.out.println(String.format("Результаты тестирования пользователя %s. Тест %s. Правильных ответов %s из необходимых %s.",
                name,
                result,
                numberCorrectAnswers,
                requiredNumberCorrectAnswers));
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