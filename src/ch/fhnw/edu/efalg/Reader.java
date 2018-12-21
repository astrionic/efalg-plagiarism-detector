package ch.fhnw.edu.efalg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Reader {

    /**
     * Private constructor to prevent the creation of instances of this class.
     */
    private Reader() {
    }

    /**
     * TODO finish documenting Reader
     *
     * @param path Relative or absolute path
     * @return An array containing all the read files as strings
     * @throws IOException If the path does not point to a directory
     */
    public static String[] readFiles(String path) throws IOException {
        // TODO Only read .java files
        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files == null) {
            throw new IOException("Not a directory.");
        }
        List<String> contents = new ArrayList<>(files.length);
        for(File file : files) {
            if(file.isFile() && file.canRead()) {
                Path filePath = Paths.get(file.getAbsolutePath());
                String content = Files.lines(filePath).collect(Collectors.joining("\n"));
                contents.add(content);
            }
        }
        String[] result = new String[contents.size()];
        return contents.toArray(result);
    }
}
