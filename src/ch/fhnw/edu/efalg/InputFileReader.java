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

// TODO Document InputFileReader. Also maybe rename?
public final class InputFileReader {

    /**
     * Private constructor to prevent the creation of instances of this class.
     */
    private InputFileReader() {
    }

    /**
     * @param path Relative or absolute path
     * @return An array containing all the readLines files as strings
     * @throws IOException If the path does not point to a directory
     */
    public static String[] readProgramFiles(final String path) throws IOException {
        final var files = readJavaFilesInDirectory(path);
        final var contents = new ArrayList<>(files.length);
        for(File file : files) {
            if(file.isFile() && file.canRead()) {
                Path filePath = Paths.get(file.getAbsolutePath());
                String content = Files.lines(filePath).collect(Collectors.joining("\n"));
                contents.add(content);
            }
        }
        final var result = new String[contents.size()];
        return contents.toArray(result);
    }

    public static String[] readProgramNames(final String path) throws IOException {
        final var files = readJavaFilesInDirectory(path);
        final var names = new ArrayList<>(files.length);
        for(File file : files) {
            if(file.isFile() && file.canRead()) {
                names.add(file.getName());
            }
        }
        final var result = new String[names.size()];
        return names.toArray(result);
    }

    private static File[] readJavaFilesInDirectory(final String path) throws IOException {
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
        return files;
    }

    public static List<String> readKeywords(final String path) throws IOException {
        final Path filePath = Paths.get(path);
        return Files.lines(filePath).collect(Collectors.toList());
    }

    public static List<Character> readSeparators(final String path) throws IOException {
        final var filePath = Paths.get(path);
        // TODO Reading without specifying encoding might break things
        final var s = new String(Files.readAllBytes(filePath));
        return s.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
    }
}
