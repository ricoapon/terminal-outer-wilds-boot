package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import nl.ricoapon.terminal.backend.filesystem.InMemoryFileSystem;

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
        return new CommandResponseEvent("I have not implemented this command yet, but we did change directory!",
                String.join(" ", parsedCommand.getArgs()));
    }
}
