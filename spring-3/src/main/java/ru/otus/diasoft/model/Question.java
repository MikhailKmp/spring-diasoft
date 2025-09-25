package ru.otus.diasoft.model;

import java.util.Map;
import java.util.Objects;

/**
 * Вопрос с вариантами ответов
 */
public class Question {

    /**
     * Вопрос
     */
    private String question;

    /**
     * Порядковый номер правильного ответа
     */
    private Integer correctAnswer;

    /**
     * Вариант ответа по его порядковому номеру
     */
    private Map<Integer, String> answerOption;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Map<Integer, String> getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(Map<Integer, String> answerOption) {
        this.answerOption = answerOption;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && Objects.equals(correctAnswer, question1.correctAnswer) && Objects.equals(answerOption, question1.answerOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, correctAnswer, answerOption);
    }
}
