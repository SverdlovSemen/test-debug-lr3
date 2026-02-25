import java.io.File;

public class FileShredder {
    public boolean shred(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete(); // Реальное удаление файла
        }
        return false;
    }
}
