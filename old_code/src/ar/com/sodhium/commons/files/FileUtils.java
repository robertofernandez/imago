package ar.com.sodhium.commons.files;

import java.io.File;

public class FileUtils {

    public static void createFoldersIfNotExists(String path) {
        File outputFolder = new File(path);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
    }

}
