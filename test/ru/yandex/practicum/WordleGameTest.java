package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.LimitStepsException;
import java.io.IOException;

class WordleGameTest {
    private static WordleDictionaryLoader loader;
    private static WordleDictionary dictionary;

    @BeforeAll
    static void init() {
        loader = new WordleDictionaryLoader("words_ru.txt");
        try {
            dictionary = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void ShouldBeReturnSignsByCurrentWord() {
        WordleGame game = new WordleGame(dictionary, "яхонт");
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("+++++", game.checkWord("яхонт"));
        });
    }

    @Test
    void ShouldBeReturnSigns() {
        WordleGame game = new WordleGame(dictionary, "яхонт");
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("+--^-", game.checkWord("ячата"));
        });
    }

    @Test
    void shouldBeLimitStepsExceptionBy7Steps() {
        WordleGame game = new WordleGame(dictionary, "яхонт");
        Assertions.assertThrows(LimitStepsException.class, () -> {
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
        });
    }

    @Test
    void shouldDontBeLimitStepsExceptionBy6Steps() {
        WordleGame game = new WordleGame(dictionary, "яхонт");
        Assertions.assertDoesNotThrow(() -> {
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
            game.checkWord("ячата");
        });
    }
}
