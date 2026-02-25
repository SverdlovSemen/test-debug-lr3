import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class FileShredderTest {
    @Test
    void testShredderCreation() {
        // Мы пытаемся создать класс, которого еще нет - код не скомпилируется
        FileShredder shredder = new FileShredder();
        assertNotNull(shredder);
    }

    @Test
    void testShredMethodInterface() {
        FileShredder shredder = new FileShredder();
        // Мы хотим, чтобы метод принимал путь и возвращал true в случае успеха
        boolean result = shredder.shred("non_existent_file.txt");
        assertFalse(result, "Should return false if file does not exist");
    }

    @Test
    void testActualFileDeletion() throws IOException {
        // 1. Создаем реальный временный файл для теста
        File tempFile = new File("test_to_delete.txt");
        tempFile.createNewFile();
        assertTrue(tempFile.exists(), "Файл должен быть создан перед удалением");

        FileShredder shredder = new FileShredder();

        // 2. Вызываем метод уничтожения
        boolean result = shredder.shred(tempFile.getAbsolutePath());

        // 3. Проверяем, что метод вернул true и файла больше нет
        assertTrue(result, "Метод должен вернуть true после удаления");
        assertFalse(tempFile.exists(), "Файл должен физически исчезнуть");
    }

    @Test
    void testFileOverwriting() throws IOException {
        // 1. Создаем файл с секретными данными
        File tempFile = new File("secret.txt");
        String originalData = "MY SECRET DATA";
        Files.writeString(tempFile.toPath(), originalData);

        FileShredder shredder = new FileShredder();

        // 2. Вызываем пока не существующий метод перезаписи
        shredder.overwrite(tempFile, '0');

        // 3. Читаем файл обратно и проверяем, что там теперь нули
        String contentAfter = Files.readString(tempFile.toPath());
        assertNotEquals(originalData, contentAfter);
        assertTrue(contentAfter.contains("0"));

        // Чистим за собой
        tempFile.delete();
    }

    @Test
    void testMultiplePasses() throws IOException {
        File tempFile = new File("multi_pass_test.txt");
        Files.writeString(tempFile.toPath(), "TOP SECRET");

        FileShredder shredder = new FileShredder();
        char[] symbols = {'0', '1', '*'}; // 3 прохода разными символами

        // Вызываем метод с массивом символов
        shredder.shredWithSymbols(tempFile.getAbsolutePath(), symbols);

        assertFalse(tempFile.exists(), "Файл должен быть удален после всех проходов");
    }

    @Test
    void testDirectoryShredding() throws IOException {
        // 1. Создаем структуру: Папка -> Файл внутри
        File tempDir = new File("test_dir");
        tempDir.mkdir();
        File fileInDir = new File(tempDir, "inner_file.txt");
        Files.writeString(fileInDir.toPath(), "Secret in folder");

        FileShredder shredder = new FileShredder();

        // 2. Пытаемся уничтожить папку
        boolean result = shredder.shred(tempDir.getAbsolutePath());

        // 3. Проверяем результат
        assertTrue(result, "Метод должен возвращать true для папок");
        assertFalse(tempDir.exists(), "Папка должна быть удалена полностью");
    }
}