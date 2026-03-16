package ru.yandex.practicum.exception;

import java.io.IOException;

public class WordleDictionaryIsEmpty extends RuntimeException {
    public WordleDictionaryIsEmpty(String message) {
        super(message);
    }
}
