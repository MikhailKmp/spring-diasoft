package ru.otus.diasoft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.diasoft.model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class QuestionService {

    private final List<Question> questions;

    public QuestionService(@Value("${questions.file}") String pathToFile) throws IOException {
        this.questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            List<String> lines = reader.lines()
                    .skip(1)
                    .collect(toList());
            for (String line : lines) {
                Question question = buildQuestion(line);
                questions.add(question);
            }
        }
    }

    /**
     * Формирование вопроса с вариантами ответов
     *
     * @param string строка с данными
     * @return вопрос
     */
    private Question buildQuestion(String string) {
        List<String> value = Arrays.stream(string.split(",")).collect(toList());
        if (value.size() != 6) {
            throw new IllegalArgumentException("Файл с вопросами составлен неверно");
        }

        Map<Integer, String> answerOption = new HashMap<>();
        answerOption.put(1, value.get(2));
        answerOption.put(2, value.get(3));
        answerOption.put(3, value.get(4));
        answerOption.put(4, value.get(5));

        Question question = new Question();
        question.setQuestion(value.get(0));
        question.setCorrectAnswer(Integer.valueOf(value.get(1)));
        question.setAnswerOption(answerOption);

        return question;
    }

    /**
     * Получение списка вопросов
     *
     * @return список вопросов
     */
    public List<Question> getQuestions() {
        return questions;
    }
}
