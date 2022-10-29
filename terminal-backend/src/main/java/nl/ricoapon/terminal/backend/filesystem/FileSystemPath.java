package nl.ricoapon.terminal.backend.filesystem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public sealed abstract class FileSystemPath {
    final static String ROOT = "/";
    final List<String> traversedDirectories;

    public FileSystemPath(List<String> traversedDirectories) {
        this.traversedDirectories = traversedDirectories;
    }

    public static FileSystemPath.Absolute ofAbsolute(String pathAsString) {
        if (!pathAsString.startsWith(ROOT)) {
            throw new RuntimeException("Given path is not absolute: " + pathAsString);
        }

        if (ROOT.equals(pathAsString)) {
            return new Absolute(List.of());
        }

        return new Absolute(Arrays.stream(pathAsString.substring(1).split("/")).toList());
    }

    public static FileSystemPath.Relative ofRelative(String pathAsString) {
        if (pathAsString.startsWith(ROOT)) {
            throw new RuntimeException("Given path is not relative: " + pathAsString);
        }

        return new Relative(Arrays.stream(pathAsString.split("/")).toList());
    }

    public static FileSystemPath of(String pathAsString) {
        if (pathAsString.startsWith(ROOT)) {
            return ofAbsolute(pathAsString);
        }
        return ofRelative(pathAsString);
    }

    public String getPathAsString() {
        return ((this instanceof Absolute) ? ROOT : "") +
                String.join("/", traversedDirectories);
    }

    public List<String> getTraversedDirectories() {
        return traversedDirectories;
    }

    public static final class Absolute extends FileSystemPath {
        public Absolute(List<String> traversedDirectories) {
            super(traversedDirectories);
        }

        public Absolute resolve(Relative relative) {
            return new Absolute(Stream.concat(traversedDirectories.stream(), relative.traversedDirectories.stream()).toList());
        }
    }

    public static final class Relative extends FileSystemPath {
        public Relative(List<String> traversedDirectories) {
            super(traversedDirectories);
        }

        public Absolute resolve(Absolute absolute) {
            return absolute.resolve(this);
        }
    }
}
