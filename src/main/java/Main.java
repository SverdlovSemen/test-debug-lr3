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
        System.out.print("Введите путь к файлу: ");
        String path = scanner.nextLine();

        System.out.print("Сколько раз перезаписать файл? ");
        int passes = scanner.nextInt();

        char[] symbols = new char[passes];
        for(int i = 0; i < passes; i++) symbols[i] = '0'; // Для простоты забиваем нулями

        try {
            shredder.shredWithSymbols(path, symbols);
            System.out.println("Файл успешно уничтожен.");
        } catch (IOException e) {
            System.err.println("Ошибка при уничтожении: " + e.getMessage());
        }
    }
}