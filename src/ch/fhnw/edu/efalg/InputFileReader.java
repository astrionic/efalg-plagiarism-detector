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

/**
 * Contains static methods to read necessary content from files.
 */
public final class InputFileReader {

    /**
     * Private constructor to prevent the creation of instances of this class.
     */
    private InputFileReader() {
    }

    /**
     * Reads the content of all files ending in .java from the given directory
     *
     * @param path Relative or absolute path to the directory
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

    /**
     * Reads the names of all files ending in .java from the given directory
     *
     * @param path Relative or absolute path to the directory
     * @return An array containing all the readLines files as strings
     * @throws IOException If the path does not point to a directory
     */
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

    /**
     * Returns all files ending in .java from a given directory
     *
     * @param path Relative or absolute path to the directory
     * @return Array containing the read {@link File} objects
     * @throws IOException If the path does not point to a directory
     */
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

    /**
     * Reads keywords from the file at the given path. Each line of the file should contain exactly one keyword.
     *
     * @param path Relative or absolute path to the file
     * @return A {@link List} containing the read keywords
     * @throws IOException If the file could not be read
     */
    public static List<String> readKeywords(final String path) throws IOException {
        final Path filePath = Paths.get(path);
        return Files.lines(filePath).collect(Collectors.toList());
    }

    /**
     * Reads separators from the file at the given path. The file should only contain the separators and nothing else.
     *
     * @param path Relative or absolute path to the file
     * @return A {@link List} containing the read separators
     * @throws IOException If the file could not be read
     */
    public static List<Character> readSeparators(final String path) throws IOException {
        final var filePath = Paths.get(path);
        // Reading without specifying encoding might break things
        final var s = new String(Files.readAllBytes(filePath));
        return s.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
    }
}
