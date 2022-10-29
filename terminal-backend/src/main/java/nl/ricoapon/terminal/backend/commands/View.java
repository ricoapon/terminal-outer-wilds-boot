package nl.ricoapon.terminal.backend.commands;

import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.FullScreenVideoEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;

import java.util.List;

public class View implements Command {
    @Override
    public String command() {
        return "view";
    }

    @Override
    public TerminalEvent process(ParsedCommand parsedCommand, TerminalState terminalState) {
        FullScreenVideoEvent.VideoLine line1 = new FullScreenVideoEvent.VideoLine();
        line1.location = "/";
        line1.command = "cd tutorial";
        FullScreenVideoEvent.VideoLine line2 = new FullScreenVideoEvent.VideoLine();
        line2.location = "tutorial";
        line2.command = "execute npc.sh";
        FullScreenVideoEvent.VideoLine line3 = new FullScreenVideoEvent.VideoLine();
        line3.response = "I am an npc";

        return new FullScreenVideoEvent(List.of(line1, line2, line3));
    }
}
