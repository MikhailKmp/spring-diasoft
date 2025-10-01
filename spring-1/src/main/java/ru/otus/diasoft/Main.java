package ru.otus.diasoft;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.diasoft.service.DiagnosticsService;

public class Main {

    /**
     * Путь к файлу с конфигурацией spring context-a
     */
    private static final String CONFIG_LOCATION = "/spring-context.xml";

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION)) {
            DiagnosticsService diagnosticsService = context.getBean(DiagnosticsService.class);
            diagnosticsService.runDiagnostics();
        }
    }
}
