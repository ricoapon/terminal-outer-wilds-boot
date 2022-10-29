package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;

public class ChangeDirectory implements Command {
    @Override
    public String command() {
        return "cd";
    }

    @Override
    public TerminalEvent process(ParsedCommand parsedCommand, String location) {
        return new CommandResponseEvent("I have not implemented this command yet, but we did change directory!",
                String.join(" ", parsedCommand.getArgs()));
    }
}
