package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;
import java.io.IOException;

public class WordleDictionaryTest {
    private static WordleDictionaryLoader loader;
    private static WordleDictionary dictionary;
    private static WordleGame game;

    @BeforeAll
    static void init() {
        loader = new WordleDictionaryLoader("words_ru.txt");
        try {
            dictionary = loader.load();
            game = new WordleGame(dictionary, "яхонт");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void shouldBeEnglishWordNotFoundInDictionaryException() {
        Assertions.assertThrows(WordNotFoundInDictionary.class, () -> {
            game.checkWord("counts");
        });
    }

    @Test
    void shouldBeWordNotFoundInDictionaryException() {
        Assertions.assertThrows(WordNotFoundInDictionary.class, () -> {
            game.checkWord("ячета");
        });
    }

    @Test
    void shouldDontBeWordNotFoundInDictionaryException() {
        Assertions.assertDoesNotThrow(() -> {
            game.checkWord("ячата");
        });
    }
}
