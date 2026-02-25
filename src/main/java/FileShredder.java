import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileShredder {
    public boolean shred(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                // Добавляем вызов перезаписи перед удалением
                overwrite(file, '0');
                return file.delete();
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public void overwrite(File file, char symbol) throws IOException {
        long length = file.length();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (long i = 0; i < length; i++) {
                fos.write(symbol);
            }
        }
    }

    public void shredWithSymbols(String path, char[] symbols) throws IOException {
        File file = new File(path);
        if (!file.exists()) return;

        // //todo: Реализовать логику нескольких проходов для безопасности
        for (char symbol : symbols) {
            overwrite(file, symbol);
        }

        file.delete();
    }
}
