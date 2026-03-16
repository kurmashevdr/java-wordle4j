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

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        dictionaryWithHints = new LinkedList<>(dictionary.getWords());
        currentWord = dictionary.getWord();
        steps = 0;
        gameOver = false;
        answers = new LinkedList<>();
    }

    public WordleGame(WordleDictionary dictionary, String currentWord) {
        this.dictionary = dictionary;
        dictionaryWithHints = new LinkedList<>(dictionary.getWords());
        this.currentWord = currentWord;
        steps = 0;
        gameOver = false;
        answers = new LinkedList<>();
    }

    public String hint() {
        if (answers.isEmpty()) {
            return dictionary.getWord();
        }
        String lastAnswer = answers.get(answers.size() - 2);
        String resultCheckAnswer = answers.getLast();
        for (int i = 0; i < resultCheckAnswer.length(); i++) {
            for (String hint : dictionary.getWords()) {
                if (resultCheckAnswer.charAt(i) == '-' && hint.contains(lastAnswer.substring(i, i + 1))) {
                    dictionaryWithHints.remove(hint);
                }
                if (resultCheckAnswer.charAt(i) == '^' && !hint.contains(lastAnswer.substring(i, i + 1))) {
                    dictionaryWithHints.remove(hint);
                }
                if (resultCheckAnswer.charAt(i) == '+' && lastAnswer.charAt(i) != hint.charAt(i)) {
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
        if (steps > 6) {
            gameOver = true;
            throw new LimitStepsException("У вас закончились попытки");
        }
        answers.add(word);
        if (word.equals(currentWord)) {
            gameOver = true;
            return "+++++";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (currentWord.charAt(i) == word.charAt(i)) {
                sb.append("+");
                continue;
            }
            boolean containsChar = currentWord.contains(word.substring(i, i + 1));
            if (containsChar) {
                sb.append("^");
            } else {
                sb.append("-");
            }
        }
        answers.add(sb.toString());
        return sb.toString();
    }

    public boolean isOver() {
        return gameOver;
    }

}
