package ru.yandex.practicum.exception;

public class WordleDictionaryIsEmpty extends RuntimeException {
    public WordleDictionaryIsEmpty(String message) {
        super(message);
    }
}
