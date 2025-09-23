package ru.otus.diasoft;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.diasoft.service.DiagnosticsService;
import ru.otus.diasoft.service.QuestionService;

public class Main {

    /**
     * Путь к файлу с конфигурацией spring context-a
     */
    private static final String CONFIG_LOCATION = "/spring-context.xml";

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION)) {
            QuestionService questionService = context.getBean(QuestionService.class);
            DiagnosticsService.runTest(questionService.getQuestions());
        }
    }
}
