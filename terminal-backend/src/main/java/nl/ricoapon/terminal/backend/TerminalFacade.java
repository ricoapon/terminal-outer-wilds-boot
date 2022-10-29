package nl.ricoapon.terminal.backend;

import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.FullScreenVideoEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;

import java.util.List;

public class TerminalFacade {
    public TerminalEvent processCommand(String command) {
        if (command.startsWith("view")) {
            FullScreenVideoEvent.VideoLine line1 = new FullScreenVideoEvent.VideoLine();
            line1.location = "/";
            line1.command = "cd tutorial";
            FullScreenVideoEvent.VideoLine line2 = new FullScreenVideoEvent.VideoLine();
            line2.location = "tutorial";
            line2.command = "execute npc.sh";
            FullScreenVideoEvent.VideoLine line3 = new FullScreenVideoEvent.VideoLine();
            line3.response = "I am an npc";

            return new FullScreenVideoEvent(List.of(line1, line2, line3));
        } else if (command.startsWith("cd ")) {
            return new CommandResponseEvent("I have not implemented this command yet, but we did change directory!", command.substring(3));
        }

        return new CommandResponseEvent("I have not implemented this command yet!", null);
    }
}
