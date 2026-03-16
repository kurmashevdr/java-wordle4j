package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;
    private final Random random;

    public WordleDictionary(List<String> words) {
        this.words = words;
        this.random = new Random();
    }

    public String getWord() {
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getWords() {
        return words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public static String wordNormalization(String word) {
        return word.toLowerCase().trim().replace("ё", "е");
    }

}
