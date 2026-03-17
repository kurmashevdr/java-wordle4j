package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordleDictionaryIsEmpty;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final String path;
    private final Charset charset;

    public WordleDictionaryLoader(String path) {
        this.path = path;
        this.charset = StandardCharsets.UTF_8;
    }

    public WordleDictionary load() throws IOException {
        List<String> words = new LinkedList<>();
        Path file = Paths.get(path);
        if (Files.exists(file)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path, charset))) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (line.length() == 5) {
                        words.add(WordleDictionary.normalizeWord(line));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new FileNotFoundException(String.format("Словарь %s не найден!", file.getFileName().toString()));
        }
        if (words.isEmpty()) {
            throw new WordleDictionaryIsEmpty(String.format("Словарь %s пуст", file.getFileName().toString()));
        }
        return new WordleDictionary(words);
    }

}
