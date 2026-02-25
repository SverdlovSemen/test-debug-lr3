import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileShredder {
    public boolean shred(String path) {
        File file = new File(path);
        if (!file.exists()) return false;

        if (file.isDirectory()) {
            // Если это папка, получаем список всех файлов внутри
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    // Рекурсивно вызываем уничтожение для каждого элемента
                    shred(child.getAbsolutePath());
                }
            }
        } else {
            // Если это файл, сначала затираем его
            try {
                overwrite(file, '0');
            } catch (IOException e) {
                return false;
            }
        }
        // В конце удаляем сам файл или теперь уже пустую папку
        return file.delete();
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
