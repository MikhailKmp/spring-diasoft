package ru.otus.diasoft;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.diasoft.service.DiagnosticsService;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        DiagnosticsService diagnosticsService = context.getBean(DiagnosticsService.class);
        diagnosticsService.runDiagnostics();
    }
}
