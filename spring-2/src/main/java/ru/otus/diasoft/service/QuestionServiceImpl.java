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
public class QuestionServiceImpl implements QuestionService {

    private final List<Question> questions;
    private final Integer indexFirstQuestion;

    public QuestionServiceImpl(@Value("${questions.file}") String pathToFile,
                               @Value("${questions.question.index.first}") Integer indexFirstQuestion) throws IOException {
        this.indexFirstQuestion = indexFirstQuestion;
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
        if (value.size() < 4) {
            throw new IllegalArgumentException("Файл с вопросами составлен неверно. " +
                    "Файл должен содержать вопрос, правильный вариант ответа и как минимум 2 варианта ответа.");
        }

        Map<Integer, String> answerOption = new HashMap<>();
        int ix = 1;
        for (int i = indexFirstQuestion; i < value.size(); i++) {
            answerOption.put(ix, value.get(i));
            ix++;
        }

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
