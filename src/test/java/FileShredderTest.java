import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileShredderTest {
    @Test
    void testShredderCreation() {
        // Мы пытаемся создать класс, которого еще нет - код не скомпилируется
        FileShredder shredder = new FileShredder();
        assertNotNull(shredder);
    }
}