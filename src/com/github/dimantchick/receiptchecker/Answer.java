package com.github.dimantchick.receiptchecker;

/**
 * Класс, описывающий ответ сервера на запрос
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class Answer {
    private String answer;
    private int httpCode;
    private Exception exception;

    private String errorMessage = "";

    /**
     * Конструктор - создание нового объекта
     *
     * @param answer    - строка ответа сервера
     * @param httpCode  - http код ответа сервера
     * @param exception - возикшее исключение
     */
    public Answer(String answer, int httpCode, Exception exception) {
        this.answer = answer;
        this.httpCode = httpCode;
        this.exception = exception;
    }

    /**
     * Геттер для строки ответа
     *
     * @return возвращает строку ответа
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Сеттер для строки ответа
     *
     * @param answer - строка ответа
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Геттер для http кода
     *
     * @return возвращает http код
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Сеттер для http кода
     *
     * @param httpCode - http код
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * Геттер для исключения
     *
     * @return возвращает исключение
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Сеттер для исключения
     *
     * @param exception - исключение
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * Геттер для текста ошибки
     *
     * @return возвращает текст ошибки
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Сеттер для текста ошибки
     *
     * @param errorMessage - текст ошибки
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
