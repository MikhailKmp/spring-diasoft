package ru.otus.diasoft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.diasoft.model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static ru.otus.diasoft.constants.MessageCodes.FORMAT_INVALID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Map<String, List<Question>> questionsByLocale;
    private final Integer indexFirstQuestion;
    private final MessageSource messageSource;

    public QuestionServiceImpl(@Value("${questions.file}") String pathToFile,
                               @Value("${questions.question.index.first}") Integer indexFirstQuestion,
                               MessageSource messageSource) throws IOException {
        this.indexFirstQuestion = indexFirstQuestion;
        this.messageSource = messageSource;
        this.questionsByLocale = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            List<String> lines = reader.lines()
                    .skip(1)
                    .collect(toList());
            for (String line : lines) {
                Question question = buildQuestion(line);
                List<Question> questions = questionsByLocale.getOrDefault(question.getLocale(), new ArrayList<>());
                questions.add(question);
                questionsByLocale.putIfAbsent(question.getLocale(), questions);
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
        if (value.size() < 5) {
            throw new IllegalArgumentException(messageSource.getMessage(FORMAT_INVALID, new Object[]{}, Locale.getDefault()));
        }

        Map<Integer, String> answerOption = new HashMap<>();
        int ix = 1;
        for (int i = indexFirstQuestion; i < value.size(); i++) {
            answerOption.put(ix, value.get(i));
            ix++;
        }

        Question question = new Question();
        question.setLocale(value.get(0));
        question.setQuestion(value.get(1));
        question.setCorrectAnswer(Integer.valueOf(value.get(2)));
        question.setAnswerOption(answerOption);

        return question;
    }

    public List<Question> getQuestions() {
        return getQuestions(Locale.getDefault().toLanguageTag());
    }

    /**
     * Получение списка вопросов в зависимости от локали
     *
     * @param locale локаль
     * @return список вопросов
     */
    public List<Question> getQuestions(String locale) {
        List<Question> questions = questionsByLocale.get(locale);
        if (questions == null) {
            questions = new ArrayList<>();
        }
        return questions;
    }
}
