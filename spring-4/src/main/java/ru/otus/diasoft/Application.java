package ru.otus.diasoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.otus.diasoft.constants.Locales;
import ru.otus.diasoft.service.DiagnosticsService;
import ru.otus.diasoft.service.QuestionService;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
@PropertySource("classpath:application.yml")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Выберите язык. Русский - 1, Английский - 2.");
            String language = scanner.nextLine();

            if (language.equals("1")) {
                Locale.setDefault(Locale.forLanguageTag(Locales.RU));
            } else {
                Locale.setDefault(Locale.ENGLISH);
            }

            QuestionService questionService = context.getBean(QuestionService.class);
            DiagnosticsService diagnosticsService = context.getBean(DiagnosticsService.class);

            diagnosticsService.runTest(questionService.getQuestions(Locale.getDefault().toLanguageTag()), scanner);
        }
    }
}
