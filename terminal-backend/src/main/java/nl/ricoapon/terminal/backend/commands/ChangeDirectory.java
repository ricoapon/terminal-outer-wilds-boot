package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import nl.ricoapon.terminal.backend.filesystem.FileSystemNode;
import nl.ricoapon.terminal.backend.filesystem.FileSystemPath;
import nl.ricoapon.terminal.backend.filesystem.InMemoryFileSystem;

import java.util.Optional;

public class ChangeDirectory implements Command {
    private final InMemoryFileSystem inMemoryFileSystem;

    public ChangeDirectory(InMemoryFileSystem inMemoryFileSystem) {
        this.inMemoryFileSystem = inMemoryFileSystem;
    }

    @Override
    public String command() {
        return "cd";
    }

    @Override
    public TerminalEvent process(ParsedCommand parsedCommand, TerminalState terminalState) {
        if (parsedCommand.getArgs().size() != 1) {
            return new CommandResponseEvent("You have to specify exactly one argument for this command");
        }

        String pathAsString = parsedCommand.getArgs().get(0);
        FileSystemPath fileSystemPath = FileSystemPath.of(pathAsString);
        FileSystemPath.Absolute absolute = switch (fileSystemPath) {
            case FileSystemPath.Absolute a -> a;
            case FileSystemPath.Relative r -> terminalState.currentDirectory.resolve(r);
        };

        Optional<FileSystemNode> fileSystemNode = inMemoryFileSystem.determineNode(absolute);
        if (fileSystemNode.isEmpty()) {
            return new CommandResponseEvent("The path does not exist");
        }

        if (!(fileSystemNode.get() instanceof FileSystemNode.Directory)) {
            return new CommandResponseEvent("Specified path is not a directory");
        }

        terminalState.currentDirectory = absolute;

        return new CommandResponseEvent(null, fileSystemNode.get().name());
    }
}
