package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import nl.ricoapon.terminal.backend.filesystem.InMemoryFileSystem;

import java.util.List;

public interface Command {
    /** The text that is used to search for when the command is typed. */
    String command();

    TerminalEvent process(ParsedCommand parsedCommand, TerminalState terminalState);

    static List<Command> allCommands(InMemoryFileSystem inMemoryFileSystem) {
        // We can improve this with Dependency Injection, but for now it works quite easily.
        return List.of(
                new Help(),
                new ChangeDirectory(inMemoryFileSystem),
                new View(),
                new Manual()
        );
    }
}
