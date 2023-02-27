package me.readyplayer.reportingservice.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.springframework.core.codec.ResourceEncoder.DEFAULT_BUFFER_SIZE;

public class FileUtils {
    public static void saveFileToDisk(InputStream inputStream) throws IOException {
        File isDir = new File("./storage/");

        if (!isDir.exists()) {
            isDir.mkdirs();
        }

        File file = new File("./storage/" + "report " + new Date() + ".pdf");

        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
