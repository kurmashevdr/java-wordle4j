package ru.yandex.practicum.exception;

public class WordNotFoundInDictionary extends RuntimeException {
    public WordNotFoundInDictionary(String message) {
        super(message);
    }
}
