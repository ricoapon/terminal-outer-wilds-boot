package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.events.TerminalEvent;

import java.util.List;

public interface Command {
    /** The text that is used to search for when the command is typed. */
    String command();

    TerminalEvent process(ParsedCommand parsedCommand, String location);

    static List<Command> allCommands() {
        // We can improve this with Dependency Injection, but for now it works quite easily.
        return List.of(
                new Help(),
                new ChangeDirectory(),
                new View()
        );
    }
}
