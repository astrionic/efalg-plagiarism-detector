package ch.fhnw.edu.efalg;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO Document Reader. Also maybe rename?
public final class Reader {

    /**
     * Private constructor to prevent the creation of instances of this class.
     */
    private Reader() {
    }

    /**
     * @param path Relative or absolute path
     * @return An array containing all the readLines files as strings
     * @throws IOException If the path does not point to a directory
     */
    public static String[] readJavaFiles(final String path) throws IOException {
        final var directory = new File(path);
        final var filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".java");
            }
        };
        final File[] files = directory.listFiles(filter);
        if(files == null) {
            throw new IOException("Not a directory.");
        }
        final List<String> contents = new ArrayList<>(files.length);
        for(File file : files) {
            if(file.isFile() && file.canRead()) {
                Path filePath = Paths.get(file.getAbsolutePath());
                String content = Files.lines(filePath).collect(Collectors.joining("\n"));
                contents.add(content);
            }
        }
        final String[] result = new String[contents.size()];
        return contents.toArray(result);
    }

    public static List<String> readJavaKeywords() throws IOException {
        final Path filePath = Paths.get("data\\java_keywords.txt");
        return Files.lines(filePath).collect(Collectors.toList());
    }

    public static List<Character> readJavaSeparators() throws IOException {
        final Path filePath = Paths.get("data\\java_separators.txt");
        // TODO Reading without specifying encoding might break things
        final var s = new String(Files.readAllBytes(filePath));
        return s.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
    }
}
