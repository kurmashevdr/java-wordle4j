package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.WordleDictionaryIsEmpty;

import java.io.FileNotFoundException;

public class WordleDictionaryLoaderTest {
    @Test
    void ShouldBeWordleDictionaryIsEmptyException() {
        Assertions.assertThrows(WordleDictionaryIsEmpty.class, () -> {
            WordleDictionaryLoader loader = new WordleDictionaryLoader("words_test.txt");
            WordleDictionary dictionary =  loader.load();
        });
    }

    @Test
    void shouldBeNotFoundDictionaryException() {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            WordleDictionaryLoader loader = new WordleDictionaryLoader("words_ru2.txt");
            WordleDictionary dictionary =  loader.load();
        });
    }

    @Test
    void shouldDontBeNotFoundDictionaryException() {
        Assertions.assertDoesNotThrow(() -> {
            WordleDictionaryLoader loader = new WordleDictionaryLoader("words_ru.txt");
            WordleDictionary dictionary =  loader.load();
        });
    }
}
