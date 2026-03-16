package ru.yandex.practicum;

import ru.yandex.practicum.exception.LimitStepsException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static final LocalDateTime time = LocalDateTime.now();
    private static final int MAX_WORD_LENGTH = 5;
    private static final int REPEAT_COUNT_5 = 5;

    public static void main(String[] args) {
        WordleDictionaryLoader loader = new WordleDictionaryLoader("words_ru.txt");
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Выберете пункт меню: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        WordleDictionary dictionary = loader.load();
                        WordleGame wordleGame = new WordleGame(dictionary);
                        while (!wordleGame.isOver()) {
                            System.out.print("Введите слово из 5 букв или нажмите Enter, чтобы получить подсказку: ");
                            String word = WordleDictionary.wordNormalization(scanner.nextLine());
                            if (word.isEmpty()) {
                                word = wordleGame.hint();
                                System.out.println(word);
                            }
                            if (word.length() != MAX_WORD_LENGTH) {
                                continue;
                            }
                            try {
                                String checkResult = wordleGame.checkWord(word);
                                System.out.println(checkResult);
                            } catch (LimitStepsException | WordNotFoundInDictionary e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "2":
                        printRules();
                        break;
                    case "3":
                        running = false;
                        break;
                    default:
                        System.out.println("Введен не корректный пункт меню");
                }
            }
        } catch (IOException e) {
            writeLogs(e);
        }
    }

    public static void printMenu() {
        System.out.println("-".repeat(REPEAT_COUNT_5) + "Меню" + "-".repeat(REPEAT_COUNT_5));
        System.out.println("1. Начать игру");
        System.out.println("2. Вывести правила игры");
        System.out.println("3. Выход");
        System.out.println("-".repeat(14));
    }

    public static void printRules() {
        System.out.println("-".repeat(REPEAT_COUNT_5) + "Правила" + "-".repeat(REPEAT_COUNT_5));
        System.out.println("1. Длина вводимого слова ровно 5 букв");
        System.out.println("2. Буква ё автоматически конвертируется в е");
        System.out.println("3. На то, чтобы угадать слово у вас есть 6 попыток");
        System.out.println("4. Все слова приводятся к нижнему регистру");
        System.out.println("5. - — им отмечается буква, которой НЕТ в загаданном слове");
        System.out.println("6. + — этим символом отмечается буква, которая ЕСТЬ в загаданном слове " +
                "и находится на правильной позиции");
        System.out.println("7. ^ — так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте");
        System.out.println("-".repeat(17));
    }

    public static void writeLogs(Throwable e) {
        Path pathToLogsDirectory = Paths.get(System.getProperty("user.dir") + "/logs");
        Path pathToLogsFile = pathToLogsDirectory.resolve(String.format("%d.%s.%d:%d-%d-%d.txt", time.getDayOfMonth(),
                time.getMonth(), time.getYear(), time.getHour(), time.getMinute(), time.getSecond()));
        if (!Files.exists(pathToLogsDirectory)) {
            try {
                Files.createDirectory(pathToLogsDirectory);
                Files.createFile(pathToLogsFile);
            } catch (IOException ex) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToLogsFile.toString(),
                StandardCharsets.UTF_8, true))) {
            writer.write("-".repeat(REPEAT_COUNT_5) + "\n");
            writer.write(e.getMessage() + "\n");
            for (StackTraceElement element : e.getStackTrace()) {
                writer.write(element.toString() + "\n");
            }
            writer.write("-".repeat(REPEAT_COUNT_5) + "\n");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
