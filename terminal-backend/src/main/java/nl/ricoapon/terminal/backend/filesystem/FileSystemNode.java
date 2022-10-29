package nl.ricoapon.terminal.backend.filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public sealed interface FileSystemNode {
    String name();

    FileSystemNode parent();

    List<FileSystemNode> children();

    record TextFile(String name, FileSystemNode parent, String text) implements FileSystemNode {
        @Override
        public List<FileSystemNode> children() {
            return List.of();
        }
    }

    record Directory(String name, FileSystemNode parent, List<FileSystemNode> children) implements FileSystemNode {
    }

    default boolean containsChildWithName(String name) {
        return children().stream().anyMatch(child -> name.equals(child.name()));
    }

    default Optional<FileSystemNode> traverse(List<String> nodeNames) {
        FileSystemNode node = this;
        for (String nodeName : nodeNames) {
            if ("..".equals(nodeName)) {
                // If we are at the root directory, there is no parent. We will allow the ".." usage anyway,
                // but we stay at the root directory.
                if (node.parent() != null) {
                    node = node.parent();
                }
                continue;
            }

            Optional<FileSystemNode> newNode = node.children().stream()
                    .filter(child -> nodeName.equals(child.name()))
                    // We may assume that we have one or zero matches.
                    .findAny();

            if (newNode.isEmpty()) {
                // The path is invalid.
                return Optional.empty();
            }

            node = newNode.get();
        }

        return Optional.of(node);
    }

    default Optional<FileSystemNode> traverse(FileSystemPath.Absolute absolute) {
        return traverse(absolute.getTraversedDirectories());
    }

    default FileSystemPath.Absolute determineAbsolutePath() {
        List<String> traversedNodes = new ArrayList<>();

        FileSystemNode node = this;
        while (node.parent() != null) {
            traversedNodes.add(node.name());
            node = node.parent();
        }

        Collections.reverse(traversedNodes);

        return new FileSystemPath.Absolute(traversedNodes);
    }
}
