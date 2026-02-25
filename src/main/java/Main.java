import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Устанавливаем кодировку вывода в консоль
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Устанавливаем кодировку ввода для Scanner
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        FileShredder shredder = new FileShredder();

        System.out.println("--- Бесследный уничтожитель файлов ---");
        System.out.print("Введите путь (файл или папка): ");
        String path = scanner.nextLine();

        File target = new File(path);
        if (!target.exists()) {
            System.out.println("Ошибка: Объект не найден по указанному пути.");
            return;
        }

        String type = target.isDirectory() ? "папка" : "файл";
        System.out.println("Обнаружен объект: " + type);

        System.out.print("Введите количество циклов перезаписи: ");
        int passes = Integer.parseInt(scanner.nextLine());

        char[] symbols = new char[passes];
        for (int i = 0; i < passes; i++) symbols[i] = (char) ('0' + (i % 10)); // Забиваем разными цифрами

        try {
            System.out.println("Начинаю процесс бесследного уничтожения...");
            shredder.shredWithSymbols(path, symbols);
            System.out.println("Успешно! Объект " + type + " и всё его содержимое стерто.");
        } catch (IOException e) {
            System.out.println("Критическая ошибка при работе: " + e.getMessage());
        }
    }
}