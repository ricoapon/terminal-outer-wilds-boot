package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import nl.ricoapon.terminal.backend.filesystem.FileSystemNode;
import nl.ricoapon.terminal.backend.filesystem.InMemoryFileSystem;

import java.util.stream.Collectors;

public class ListDirectories implements Command {
    private final InMemoryFileSystem inMemoryFileSystem;

    public ListDirectories(InMemoryFileSystem inMemoryFileSystem) {
        this.inMemoryFileSystem = inMemoryFileSystem;
    }

    @Override
    public String command() {
        return "ls";
    }

    @Override
    public TerminalEvent process(ParsedCommand parsedCommand, TerminalState terminalState) {
        FileSystemNode fileSystemNode = inMemoryFileSystem.determineNode(terminalState.currentDirectory).orElseThrow();
        String output = fileSystemNode.children().stream().map(FileSystemNode::name)
                .sorted()
                .collect(Collectors.joining("\n"));
        return new CommandResponseEvent(output);
    }
}
