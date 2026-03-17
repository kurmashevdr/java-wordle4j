package ru.yandex.practicum;

import ru.yandex.practicum.exception.LimitStepsException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String currentWord;
    private int steps;
    private WordleDictionary dictionary;
    private boolean gameOver;
    private List<String> answers;
    private List<String> dictionaryWithHints;
    private static final int MAX_STEPS_COUNT = 6;
    private static final int NUMBER_LAST_ANSWER_FROM_THE_END = 2;
    private static final char CHAR_NOT_FOUND_IN_WORD = '-';
    private static final char CHAR_FOUND_IN_WORD = '^';
    private static final char CORRECT_CHAR_IN_WORD_POSITION = '+';

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.dictionaryWithHints = new LinkedList<>(dictionary.getWords());
        this.currentWord = dictionary.getWord();
        this.steps = 0;
        this.gameOver = false;
        this.answers = new LinkedList<>();
    }

    public WordleGame(WordleDictionary dictionary, String currentWord) {
        this.dictionary = dictionary;
        this.dictionaryWithHints = new LinkedList<>(dictionary.getWords());
        this.currentWord = currentWord;
        this.steps = 0;
        this.gameOver = false;
        this.answers = new LinkedList<>();
    }

    public String hint() {
        if (answers.isEmpty()) {
            return dictionary.getWord();
        }
        String lastAnswer = answers.get(answers.size() - NUMBER_LAST_ANSWER_FROM_THE_END);
        String resultCheckAnswer = answers.getLast();
        for (int i = 0; i < resultCheckAnswer.length(); i++) {
            for (String hint : dictionary.getWords()) {
                boolean hintContainsChar = hint.contains(lastAnswer.substring(i, i + 1));
                if (resultCheckAnswer.charAt(i) == CHAR_NOT_FOUND_IN_WORD && hintContainsChar) {
                    dictionaryWithHints.remove(hint);
                }
                if (resultCheckAnswer.charAt(i) == CHAR_FOUND_IN_WORD && !hintContainsChar) {
                    dictionaryWithHints.remove(hint);
                }
                if (resultCheckAnswer.charAt(i) == CORRECT_CHAR_IN_WORD_POSITION
                        && lastAnswer.charAt(i) != hint.charAt(i)) {
                    dictionaryWithHints.remove(hint);
                }
            }
        }
        return dictionaryWithHints.get(new Random().nextInt(dictionaryWithHints.size()));
    }

    public String checkWord(String word) throws WordNotFoundInDictionary, LimitStepsException {
        if (!dictionary.contains(word)) {
            throw new WordNotFoundInDictionary("Такого слова нет в словаре");
        }
        steps++;
        if (steps > MAX_STEPS_COUNT) {
            gameOver = true;
            throw new LimitStepsException("У вас закончились попытки");
        }
        answers.add(word);
        if (word.equals(currentWord)) {
            gameOver = true;
            return ("" + CORRECT_CHAR_IN_WORD_POSITION).repeat(5);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (currentWord.charAt(i) == word.charAt(i)) {
                sb.append(CORRECT_CHAR_IN_WORD_POSITION);
                continue;
            }
            boolean containsChar = currentWord.contains(word.substring(i, i + 1));
            if (containsChar) {
                sb.append(CHAR_FOUND_IN_WORD);
            } else {
                sb.append(CHAR_NOT_FOUND_IN_WORD);
            }
        }
        answers.add(sb.toString());
        return sb.toString();
    }

    public boolean isOver() {
        return gameOver;
    }

}
