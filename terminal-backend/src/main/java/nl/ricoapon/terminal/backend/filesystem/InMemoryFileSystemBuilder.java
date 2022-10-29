package nl.ricoapon.terminal.backend.filesystem;

import java.util.ArrayList;

public class InMemoryFileSystemBuilder {
    private final FileSystemNode.Directory root = new FileSystemNode.Directory("root", null, new ArrayList<>());

    public InMemoryFileSystemBuilder addDirectory(FileSystemPath.Absolute parent, String name) {
        FileSystemNode parentNode = root.traverse(parent).orElseThrow();
        if (parentNode.containsChildWithName(name)) {
            throw new RuntimeException("We cannot add directory with name" + name + ", because it already exists");
        }

        FileSystemNode.Directory directory = new FileSystemNode.Directory(name, parentNode, new ArrayList<>());
        parentNode.children().add(directory);
        return this;
    }

    public InMemoryFileSystemBuilder addTextFile(FileSystemPath.Absolute parent, String name, String text) {
        FileSystemNode parentNode = root.traverse(parent).orElseThrow();

        if (parentNode.containsChildWithName(name)) {
            throw new RuntimeException("We cannot add file with name" + name + ", because it already exists");
        }

        FileSystemNode.TextFile textFile = new FileSystemNode.TextFile(name, parentNode, text);
        parentNode.children().add(textFile);
        return this;
    }

    public FileSystemNode.Directory build() {
        return root;
    }

    public static InMemoryFileSystem create() {
        return new InMemoryFileSystem(new InMemoryFileSystemBuilder()
                .addTextFile(FileSystemPath.ofAbsolute("/"), "Hello.txt", "Hey there sexy beast")
                .addDirectory(FileSystemPath.ofAbsolute("/"), "dir1")
                .addDirectory(FileSystemPath.ofAbsolute("/dir1"), "subdir1")
                .build());
    }
}
