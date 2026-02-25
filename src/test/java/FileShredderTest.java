import org.junit.jupiter.api.Test;
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
}