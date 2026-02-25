import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

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
}