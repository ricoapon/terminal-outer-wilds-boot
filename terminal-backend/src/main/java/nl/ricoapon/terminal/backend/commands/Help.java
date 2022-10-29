package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.ResourceReader;
import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.FullScreenTextEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;

public class Help implements Command {
    private final String helpFullText;

    public Help() {
        helpFullText = ResourceReader.readTextFromResource("/commands/help/help.txt");
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public TerminalEvent process(ParsedCommand parsedCommand, TerminalState terminalState) {
        return new FullScreenTextEvent(helpFullText);
    }
}
