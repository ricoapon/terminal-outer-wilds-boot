package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.ResourceReader;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.FullScreenTextEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Manual implements Command {
    private final Map<String, String> commandManuals;

    public Manual() {
        commandManuals = Stream.of("cd", "help", "ls", "man", "move", "pwd")
                .collect(Collectors.toMap(
                        Function.identity(),
                        command -> ResourceReader.readTextFromResource("/commands/man/" + command + ".txt")));
    }

    @Override
    public String command() {
        return "man";
    }

    @Override
    public TerminalEvent process(ParsedCommand parsedCommand, String location) {
        if (parsedCommand.getArgs().size() == 0) {
            return new CommandResponseEvent("No command to lookup in the manual was given");
        }

        String commandForManual = parsedCommand.getArgs().get(0);
        if (!commandManuals.containsKey(commandForManual)) {
            return new CommandResponseEvent("Manual for '" + commandForManual + "' was not found");
        }

        return new FullScreenTextEvent(commandManuals.get(commandForManual));
    }
}
