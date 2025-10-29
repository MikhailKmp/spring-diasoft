package ru.otus.diasoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.otus.diasoft.constants.Locales;
import ru.otus.diasoft.service.DiagnosticsService;
import ru.otus.diasoft.service.DiagnosticsServiceImpl;
import ru.otus.diasoft.service.QuestionService;
import ru.otus.diasoft.service.QuestionServiceImpl;

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

            DiagnosticsService diagnosticsService = context.getBean(DiagnosticsServiceImpl.class);
            diagnosticsService.runDiagnostics(scanner);
        }
    }
}
