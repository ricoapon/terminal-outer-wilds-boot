package nl.ricoapon.terminal.backend.commands;

import java.util.Arrays;
import java.util.List;

public class ParsedCommand {
    private final String command;
    private final List<String> args;

    private ParsedCommand(String command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    public static ParsedCommand of(String fullCommand) {
        String[] commandWithArgs = fullCommand.split(" ");
        String command = commandWithArgs[0];
        List<String> args = Arrays.stream(commandWithArgs)
                // Remove the first element, which is the command.
                .skip(1)
                .toList();

        return new ParsedCommand(command, args);
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }
}
