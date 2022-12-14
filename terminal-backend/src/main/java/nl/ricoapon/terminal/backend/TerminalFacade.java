package nl.ricoapon.terminal.backend;

import nl.ricoapon.terminal.backend.commands.Command;
import nl.ricoapon.terminal.backend.commands.ParsedCommand;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import nl.ricoapon.terminal.backend.filesystem.InMemoryFileSystemBuilder;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TerminalFacade {
    private final Map<String, Command> allCommands;

    public TerminalFacade() {
        allCommands = Command.allCommands(InMemoryFileSystemBuilder.create()).stream()
                .collect(Collectors.toMap(Command::command, Function.identity()));
    }

    public TerminalEvent processCommand(String command, TerminalState terminalState) {
        ParsedCommand parsedCommand = ParsedCommand.of(command);
        if (!allCommands.containsKey(parsedCommand.getCommand())) {
            return new CommandResponseEvent("Command '" + parsedCommand.getCommand() + "' not recognized");
        }

        return allCommands.get(parsedCommand.getCommand()).process(parsedCommand, terminalState);
    }
}
