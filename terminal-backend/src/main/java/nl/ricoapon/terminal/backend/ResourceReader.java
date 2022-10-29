package nl.ricoapon.terminal.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourceReader {
    /**
     * @param path The path of the resource relative to the "resources" directory (so start with a slash).
     * @return The content of the resource.
     */
    public static String readTextFromResource(String path) {
        if (!path.startsWith("/")) {
            throw new RuntimeException("Developer messed up: should start with a slash!");
        }

        try {
            return Files.readString(Paths.get(Objects.requireNonNull(ResourceReader.class.getResource(path)).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Could not read resource " + path, e);
        }
    }
}
