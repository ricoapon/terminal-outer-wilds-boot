package nl.ricoapon.terminal.backend.filesystem;

import java.util.Optional;

public class InMemoryFileSystem {
    private final FileSystemNode.Directory root;

    public InMemoryFileSystem(FileSystemNode.Directory root) {
        this.root = root;
    }

    public Optional<FileSystemNode> determineNode(FileSystemPath.Absolute absolute) {
        return root.traverse(absolute.getTraversedDirectories());
    }
}
